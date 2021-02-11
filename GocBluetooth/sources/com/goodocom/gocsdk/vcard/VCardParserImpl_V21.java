package com.goodocom.gocsdk.vcard;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.goodocom.gocsdk.vcard.exception.VCardAgentNotSupportedException;
import com.goodocom.gocsdk.vcard.exception.VCardException;
import com.goodocom.gocsdk.vcard.exception.VCardInvalidCommentLineException;
import com.goodocom.gocsdk.vcard.exception.VCardInvalidLineException;
import com.goodocom.gocsdk.vcard.exception.VCardVersionException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* access modifiers changed from: package-private */
public class VCardParserImpl_V21 {
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_ENCODING = "8BIT";
    private static final String LOG_TAG = "vCard";
    private static final int STATE_GROUP_OR_PROPERTY_NAME = 0;
    private static final int STATE_PARAMS = 1;
    private static final int STATE_PARAMS_IN_DQUOTE = 2;
    private boolean mCanceled;
    protected String mCurrentCharset;
    protected String mCurrentEncoding;
    protected final String mIntermediateCharset;
    private final List<VCardInterpreter> mInterpreterList;
    protected CustomBufferedReader mReader;
    protected final Set<String> mUnknownTypeSet;
    protected final Set<String> mUnknownValueSet;

    /* access modifiers changed from: protected */
    public static final class CustomBufferedReader extends BufferedReader {
        private String mNextLine;
        private boolean mNextLineIsValid;
        private long mTime;

        public CustomBufferedReader(Reader in) {
            super(in);
        }

        @Override // java.io.BufferedReader
        public String readLine() throws IOException {
            if (this.mNextLineIsValid) {
                String ret = this.mNextLine;
                this.mNextLine = null;
                this.mNextLineIsValid = false;
                return ret;
            }
            long start = System.currentTimeMillis();
            String line = super.readLine();
            this.mTime += System.currentTimeMillis() - start;
            return line;
        }

        public String peekLine() throws IOException {
            if (!this.mNextLineIsValid) {
                long start = System.currentTimeMillis();
                String line = super.readLine();
                this.mTime += System.currentTimeMillis() - start;
                this.mNextLine = line;
                this.mNextLineIsValid = true;
            }
            return this.mNextLine;
        }

        public long getTotalmillisecond() {
            return this.mTime;
        }
    }

    public VCardParserImpl_V21() {
        this(VCardConfig.VCARD_TYPE_DEFAULT);
    }

    public VCardParserImpl_V21(int vcardType) {
        this.mInterpreterList = new ArrayList();
        this.mUnknownTypeSet = new HashSet();
        this.mUnknownValueSet = new HashSet();
        this.mIntermediateCharset = VCardConfig.DEFAULT_INTERMEDIATE_CHARSET;
    }

    /* access modifiers changed from: protected */
    public boolean isValidPropertyName(String propertyName) {
        if (getKnownPropertyNameSet().contains(propertyName.toUpperCase()) || propertyName.startsWith("X-") || this.mUnknownTypeSet.contains(propertyName)) {
            return true;
        }
        this.mUnknownTypeSet.add(propertyName);
        Log.w(LOG_TAG, "Property name unsupported by vCard 2.1: " + propertyName);
        return true;
    }

    /* access modifiers changed from: protected */
    public String getLine() throws IOException {
        return this.mReader.readLine();
    }

    /* access modifiers changed from: protected */
    public String peekLine() throws IOException {
        return this.mReader.peekLine();
    }

    /* access modifiers changed from: protected */
    public String getNonEmptyLine() throws IOException, VCardException {
        String line;
        do {
            line = getLine();
            if (line == null) {
                throw new VCardException("Reached end of buffer.");
            }
        } while (line.trim().length() <= 0);
        return line;
    }

    private boolean parseOneVCard() throws IOException, VCardException {
        this.mCurrentEncoding = "8BIT";
        this.mCurrentCharset = "UTF-8";
        if (!readBeginVCard(true)) {
            return false;
        }
        for (VCardInterpreter interpreter : this.mInterpreterList) {
            interpreter.onEntryStarted();
        }
        parseItems();
        for (VCardInterpreter interpreter2 : this.mInterpreterList) {
            interpreter2.onEntryEnded();
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public boolean readBeginVCard(boolean allowGarbage) throws IOException, VCardException {
        while (true) {
            String line = getLine();
            if (line == null) {
                return false;
            }
            if (line.trim().length() > 0) {
                String[] strArray = line.split(":", 2);
                if (strArray.length == 2 && strArray[0].trim().equalsIgnoreCase(VCardConstants.PROPERTY_BEGIN) && strArray[1].trim().equalsIgnoreCase("VCARD")) {
                    return true;
                }
                if (!allowGarbage) {
                    throw new VCardException("Expected String \"BEGIN:VCARD\" did not come (Instead, \"" + line + "\" came)");
                } else if (!allowGarbage) {
                    throw new VCardException("Reached where must not be reached.");
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void parseItems() throws IOException, VCardException {
        boolean ended = false;
        try {
            ended = parseItem();
        } catch (VCardInvalidCommentLineException e) {
            Log.e(LOG_TAG, "Invalid line which looks like some comment was found. Ignored.");
        }
        while (!ended) {
            try {
                ended = parseItem();
            } catch (VCardInvalidCommentLineException e2) {
                Log.e(LOG_TAG, "Invalid line which looks like some comment was found. Ignored.");
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean parseItem() throws IOException, VCardException {
        this.mCurrentEncoding = "8BIT";
        VCardProperty propertyData = constructPropertyData(getNonEmptyLine());
        String propertyNameUpper = propertyData.getName().toUpperCase();
        String propertyRawValue = propertyData.getRawValue();
        if (propertyNameUpper.equals(VCardConstants.PROPERTY_BEGIN)) {
            if (propertyRawValue.equalsIgnoreCase("VCARD")) {
                handleNest();
                return false;
            }
            throw new VCardException("Unknown BEGIN type: " + propertyRawValue);
        } else if (!propertyNameUpper.equals(VCardConstants.PROPERTY_END)) {
            parseItemInter(propertyData, propertyNameUpper);
            return false;
        } else if (propertyRawValue.equalsIgnoreCase("VCARD")) {
            return true;
        } else {
            throw new VCardException("Unknown END type: " + propertyRawValue);
        }
    }

    private void parseItemInter(VCardProperty property, String propertyNameUpper) throws IOException, VCardException {
        String propertyRawValue = property.getRawValue();
        if (propertyNameUpper.equals(VCardConstants.PROPERTY_AGENT)) {
            handleAgent(property);
        } else if (!isValidPropertyName(propertyNameUpper)) {
            throw new VCardException("Unknown property name: \"" + propertyNameUpper + "\"");
        } else if (!propertyNameUpper.equals(VCardConstants.PROPERTY_VERSION) || propertyRawValue.equals(getVersionString())) {
            handlePropertyValue(property, propertyNameUpper);
        } else {
            throw new VCardVersionException("Incompatible version: " + propertyRawValue + " != " + getVersionString());
        }
    }

    private void handleNest() throws IOException, VCardException {
        for (VCardInterpreter interpreter : this.mInterpreterList) {
            interpreter.onEntryStarted();
        }
        parseItems();
        for (VCardInterpreter interpreter2 : this.mInterpreterList) {
            interpreter2.onEntryEnded();
        }
    }

    /* access modifiers changed from: protected */
    public VCardProperty constructPropertyData(String line) throws VCardException {
        VCardProperty propertyData = new VCardProperty();
        int length = line.length();
        if (length <= 0 || line.charAt(0) != '#') {
            int state = 0;
            int nameIndex = 0;
            for (int i = 0; i < length; i++) {
                char ch = line.charAt(i);
                String str = "";
                if (state != 0) {
                    if (state != 1) {
                        if (state == 2 && ch == '\"') {
                            if (VCardConstants.VERSION_V21.equalsIgnoreCase(getVersionString())) {
                                Log.w(LOG_TAG, "Double-quoted params found in vCard 2.1. Silently allow it");
                            }
                            state = 1;
                        }
                    } else if (ch == '\"') {
                        if (VCardConstants.VERSION_V21.equalsIgnoreCase(getVersionString())) {
                            Log.w(LOG_TAG, "Double-quoted params found in vCard 2.1. Silently allow it");
                        }
                        state = 2;
                    } else if (ch == ';') {
                        handleParams(propertyData, line.substring(nameIndex, i));
                        nameIndex = i + 1;
                    } else if (ch == ':') {
                        handleParams(propertyData, line.substring(nameIndex, i));
                        if (i < length - 1) {
                            str = line.substring(i + 1);
                        }
                        propertyData.setRawValue(str);
                        return propertyData;
                    }
                } else if (ch == ':') {
                    propertyData.setName(line.substring(nameIndex, i));
                    if (i < length - 1) {
                        str = line.substring(i + 1);
                    }
                    propertyData.setRawValue(str);
                    return propertyData;
                } else if (ch == '.') {
                    String groupName = line.substring(nameIndex, i);
                    if (groupName.length() == 0) {
                        Log.w(LOG_TAG, "Empty group found. Ignoring.");
                    } else {
                        propertyData.addGroup(groupName);
                    }
                    nameIndex = i + 1;
                } else if (ch == ';') {
                    propertyData.setName(line.substring(nameIndex, i));
                    nameIndex = i + 1;
                    state = 1;
                }
            }
            throw new VCardInvalidLineException("Invalid line: \"" + line + "\"");
        }
        throw new VCardInvalidCommentLineException();
    }

    /* access modifiers changed from: protected */
    public void handleParams(VCardProperty propertyData, String params) throws VCardException {
        String[] strArray = params.split("=", 2);
        if (strArray.length == 2) {
            String paramName = strArray[0].trim().toUpperCase();
            String paramValue = strArray[1].trim();
            if (paramName.equals(VCardConstants.PARAM_TYPE)) {
                handleType(propertyData, paramValue);
            } else if (paramName.equals(VCardConstants.PARAM_VALUE)) {
                handleValue(propertyData, paramValue);
            } else if (paramName.equals(VCardConstants.PARAM_ENCODING)) {
                handleEncoding(propertyData, paramValue.toUpperCase());
            } else if (paramName.equals(VCardConstants.PARAM_CHARSET)) {
                handleCharset(propertyData, paramValue);
            } else if (paramName.equals(VCardConstants.PARAM_LANGUAGE)) {
                handleLanguage(propertyData, paramValue);
            } else if (paramName.startsWith("X-")) {
                handleAnyParam(propertyData, paramName, paramValue);
            } else {
                throw new VCardException("Unknown type \"" + paramName + "\"");
            }
        } else {
            handleParamWithoutName(propertyData, strArray[0]);
        }
    }

    /* access modifiers changed from: protected */
    public void handleParamWithoutName(VCardProperty propertyData, String paramValue) {
        handleType(propertyData, paramValue);
    }

    /* access modifiers changed from: protected */
    public void handleType(VCardProperty propertyData, String ptypeval) {
        if (!getKnownTypeSet().contains(ptypeval.toUpperCase()) && !ptypeval.startsWith("X-") && !this.mUnknownTypeSet.contains(ptypeval)) {
            this.mUnknownTypeSet.add(ptypeval);
            Log.w(LOG_TAG, String.format("TYPE unsupported by %s: ", Integer.valueOf(getVersion()), ptypeval));
        }
        propertyData.addParameter(VCardConstants.PARAM_TYPE, ptypeval);
    }

    /* access modifiers changed from: protected */
    public void handleValue(VCardProperty propertyData, String pvalueval) {
        if (!getKnownValueSet().contains(pvalueval.toUpperCase()) && !pvalueval.startsWith("X-") && !this.mUnknownValueSet.contains(pvalueval)) {
            this.mUnknownValueSet.add(pvalueval);
            Log.w(LOG_TAG, String.format("The value unsupported by TYPE of %s: ", Integer.valueOf(getVersion()), pvalueval));
        }
        propertyData.addParameter(VCardConstants.PARAM_VALUE, pvalueval);
    }

    /* access modifiers changed from: protected */
    public void handleEncoding(VCardProperty propertyData, String pencodingval) throws VCardException {
        if (getAvailableEncodingSet().contains(pencodingval) || pencodingval.startsWith("X-")) {
            propertyData.addParameter(VCardConstants.PARAM_ENCODING, pencodingval);
            this.mCurrentEncoding = pencodingval.toUpperCase();
            return;
        }
        throw new VCardException("Unknown encoding \"" + pencodingval + "\"");
    }

    /* access modifiers changed from: protected */
    public void handleCharset(VCardProperty propertyData, String charsetval) {
        this.mCurrentCharset = charsetval;
        propertyData.addParameter(VCardConstants.PARAM_CHARSET, charsetval);
    }

    /* access modifiers changed from: protected */
    public void handleLanguage(VCardProperty propertyData, String langval) throws VCardException {
        String[] strArray = langval.split("-");
        if (strArray.length == 2) {
            String tmp = strArray[0];
            int length = tmp.length();
            for (int i = 0; i < length; i++) {
                if (!isAsciiLetter(tmp.charAt(i))) {
                    throw new VCardException("Invalid Language: \"" + langval + "\"");
                }
            }
            String tmp2 = strArray[1];
            int length2 = tmp2.length();
            for (int i2 = 0; i2 < length2; i2++) {
                if (!isAsciiLetter(tmp2.charAt(i2))) {
                    throw new VCardException("Invalid Language: \"" + langval + "\"");
                }
            }
            propertyData.addParameter(VCardConstants.PARAM_LANGUAGE, langval);
            return;
        }
        throw new VCardException("Invalid Language: \"" + langval + "\"");
    }

    private boolean isAsciiLetter(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return true;
        }
        if (ch < 'A' || ch > 'Z') {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void handleAnyParam(VCardProperty propertyData, String paramName, String paramValue) {
        propertyData.addParameter(paramName, paramValue);
    }

    /* access modifiers changed from: protected */
    public void handlePropertyValue(VCardProperty property, String propertyName) throws IOException, VCardException {
        String propertyNameUpper = property.getName().toUpperCase();
        String propertyRawValue = property.getRawValue();
        Collection<String> charsetCollection = property.getParameters(VCardConstants.PARAM_CHARSET);
        String targetCharset = charsetCollection != null ? charsetCollection.iterator().next() : null;
        if (TextUtils.isEmpty(targetCharset)) {
            targetCharset = "UTF-8";
        }
        if (propertyNameUpper.equals(VCardConstants.PROPERTY_ADR) || propertyNameUpper.equals(VCardConstants.PROPERTY_ORG) || propertyNameUpper.equals(VCardConstants.PROPERTY_N)) {
            handleAdrOrgN(property, propertyRawValue, VCardConfig.DEFAULT_INTERMEDIATE_CHARSET, targetCharset);
        } else if (this.mCurrentEncoding.equals(VCardConstants.PARAM_ENCODING_QP) || (propertyNameUpper.equals(VCardConstants.PROPERTY_FN) && property.getParameters(VCardConstants.PARAM_ENCODING) == null && VCardUtils.appearsLikeAndroidVCardQuotedPrintable(propertyRawValue))) {
            String quotedPrintablePart = getQuotedPrintablePart(propertyRawValue);
            String propertyEncodedValue = VCardUtils.parseQuotedPrintable(quotedPrintablePart, false, VCardConfig.DEFAULT_INTERMEDIATE_CHARSET, targetCharset);
            property.setRawValue(quotedPrintablePart);
            property.setValues(propertyEncodedValue);
            for (VCardInterpreter interpreter : this.mInterpreterList) {
                interpreter.onPropertyCreated(property);
            }
        } else if (this.mCurrentEncoding.equals(VCardConstants.PARAM_ENCODING_BASE64) || this.mCurrentEncoding.equals(VCardConstants.PARAM_ENCODING_B)) {
            try {
                try {
                    property.setByteValue(Base64.decode(getBase64(propertyRawValue), 0));
                    for (VCardInterpreter interpreter2 : this.mInterpreterList) {
                        interpreter2.onPropertyCreated(property);
                    }
                } catch (IllegalArgumentException e) {
                    throw new VCardException("Decode error on base64 photo: " + propertyRawValue);
                }
            } catch (OutOfMemoryError e2) {
                Log.e(LOG_TAG, "OutOfMemoryError happened during parsing BASE64 data!");
                for (VCardInterpreter interpreter3 : this.mInterpreterList) {
                    interpreter3.onPropertyCreated(property);
                }
            }
        } else {
            if (!this.mCurrentEncoding.equals(VCardConstants.PARAM_ENCODING_7BIT) && !this.mCurrentEncoding.equals("8BIT") && !this.mCurrentEncoding.startsWith("X-")) {
                Log.w(LOG_TAG, String.format("The encoding \"%s\" is unsupported by vCard %s", this.mCurrentEncoding, getVersionString()));
            }
            if (getVersion() == 0) {
                StringBuilder builder = null;
                while (true) {
                    String nextLine = peekLine();
                    if (TextUtils.isEmpty(nextLine) || nextLine.charAt(0) != ' ' || "END:VCARD".contains(nextLine.toUpperCase())) {
                        break;
                    }
                    getLine();
                    if (builder == null) {
                        builder = new StringBuilder();
                        builder.append(propertyRawValue);
                    }
                    builder.append(nextLine.substring(1));
                }
                if (builder != null) {
                    propertyRawValue = builder.toString();
                }
            }
            ArrayList<String> propertyValueList = new ArrayList<>();
            propertyValueList.add(maybeUnescapeText(VCardUtils.convertStringCharset(propertyRawValue, VCardConfig.DEFAULT_INTERMEDIATE_CHARSET, targetCharset)));
            property.setValues(propertyValueList);
            for (VCardInterpreter interpreter4 : this.mInterpreterList) {
                interpreter4.onPropertyCreated(property);
            }
        }
    }

    private void handleAdrOrgN(VCardProperty property, String propertyRawValue, String sourceCharset, String targetCharset) throws VCardException, IOException {
        List<String> encodedValueList = new ArrayList<>();
        if (this.mCurrentEncoding.equals(VCardConstants.PARAM_ENCODING_QP)) {
            String quotedPrintablePart = getQuotedPrintablePart(propertyRawValue);
            property.setRawValue(quotedPrintablePart);
            for (String quotedPrintableValue : VCardUtils.constructListFromValue(quotedPrintablePart, getVersion())) {
                encodedValueList.add(VCardUtils.parseQuotedPrintable(quotedPrintableValue, false, sourceCharset, targetCharset));
            }
        } else {
            for (String value : VCardUtils.constructListFromValue(VCardUtils.convertStringCharset(getPotentialMultiline(propertyRawValue), sourceCharset, targetCharset), getVersion())) {
                encodedValueList.add(value);
            }
        }
        property.setValues(encodedValueList);
        for (VCardInterpreter interpreter : this.mInterpreterList) {
            interpreter.onPropertyCreated(property);
        }
    }

    private String getQuotedPrintablePart(String firstString) throws IOException, VCardException {
        if (!firstString.trim().endsWith("=")) {
            return firstString;
        }
        int pos = firstString.length() - 1;
        do {
        } while (firstString.charAt(pos) != '=');
        StringBuilder builder = new StringBuilder();
        builder.append(firstString.substring(0, pos + 1));
        builder.append(VCardBuilder.VCARD_END_OF_LINE);
        while (true) {
            String line = getLine();
            if (line == null) {
                throw new VCardException("File ended during parsing a Quoted-Printable String");
            } else if (line.trim().endsWith("=")) {
                int pos2 = line.length() - 1;
                do {
                } while (line.charAt(pos2) != '=');
                builder.append(line.substring(0, pos2 + 1));
                builder.append(VCardBuilder.VCARD_END_OF_LINE);
            } else {
                builder.append(line);
                return builder.toString();
            }
        }
    }

    private String getPotentialMultiline(String firstString) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(firstString);
        while (true) {
            String line = peekLine();
            if (line == null || line.length() == 0 || getPropertyNameUpperCase(line) != null) {
                break;
            }
            getLine();
            builder.append(" ");
            builder.append(line);
        }
        return builder.toString();
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x0037, code lost:
        android.util.Log.w(com.goodocom.gocsdk.vcard.VCardParserImpl_V21.LOG_TAG, "Found a next property during parsing a BASE64 string, which must not contain semi-colon or colon. Treat the line as next property.");
        android.util.Log.w(com.goodocom.gocsdk.vcard.VCardParserImpl_V21.LOG_TAG, "Problematic line: " + r1.trim());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getBase64(java.lang.String r7) throws java.io.IOException, com.goodocom.gocsdk.vcard.exception.VCardException {
        /*
            r6 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r7)
        L_0x0008:
            java.lang.String r1 = r6.peekLine()
            if (r1 == 0) goto L_0x005c
            java.lang.String r2 = r6.getPropertyNameUpperCase(r1)
            java.util.Set r3 = r6.getKnownPropertyNameSet()
            boolean r3 = r3.contains(r2)
            if (r3 != 0) goto L_0x0037
            java.lang.String r3 = "X-ANDROID-CUSTOM"
            boolean r3 = r3.equals(r2)
            if (r3 == 0) goto L_0x0025
            goto L_0x0037
        L_0x0025:
            r6.getLine()
            int r3 = r1.length()
            if (r3 != 0) goto L_0x002f
            goto L_0x0057
        L_0x002f:
            java.lang.String r3 = r1.trim()
            r0.append(r3)
            goto L_0x0008
        L_0x0037:
            java.lang.String r3 = "vCard"
            java.lang.String r4 = "Found a next property during parsing a BASE64 string, which must not contain semi-colon or colon. Treat the line as next property."
            android.util.Log.w(r3, r4)
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Problematic line: "
            r4.append(r5)
            java.lang.String r5 = r1.trim()
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            android.util.Log.w(r3, r4)
        L_0x0057:
            java.lang.String r1 = r0.toString()
            return r1
        L_0x005c:
            com.goodocom.gocsdk.vcard.exception.VCardException r2 = new com.goodocom.gocsdk.vcard.exception.VCardException
            java.lang.String r3 = "File ended during parsing BASE64 binary"
            r2.<init>(r3)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.goodocom.gocsdk.vcard.VCardParserImpl_V21.getBase64(java.lang.String):java.lang.String");
    }

    private String getPropertyNameUpperCase(String line) {
        int minIndex;
        int colonIndex = line.indexOf(":");
        if (colonIndex <= -1) {
            return null;
        }
        int semiColonIndex = line.indexOf(";");
        if (colonIndex == -1) {
            minIndex = semiColonIndex;
        } else if (semiColonIndex == -1) {
            minIndex = colonIndex;
        } else {
            minIndex = Math.min(colonIndex, semiColonIndex);
        }
        return line.substring(0, minIndex).toUpperCase();
    }

    /* access modifiers changed from: protected */
    public void handleAgent(VCardProperty property) throws VCardException {
        if (!property.getRawValue().toUpperCase().contains("BEGIN:VCARD")) {
            for (VCardInterpreter interpreter : this.mInterpreterList) {
                interpreter.onPropertyCreated(property);
            }
            return;
        }
        throw new VCardAgentNotSupportedException("AGENT Property is not supported now.");
    }

    /* access modifiers changed from: protected */
    public String maybeUnescapeText(String text) {
        return text;
    }

    /* access modifiers changed from: protected */
    public String maybeUnescapeCharacter(char ch) {
        return unescapeCharacter(ch);
    }

    static String unescapeCharacter(char ch) {
        if (ch == '\\' || ch == ';' || ch == ':' || ch == ',') {
            return String.valueOf(ch);
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public int getVersion() {
        return 0;
    }

    /* access modifiers changed from: protected */
    public String getVersionString() {
        return VCardConstants.VERSION_V21;
    }

    /* access modifiers changed from: protected */
    public Set<String> getKnownPropertyNameSet() {
        return VCardParser_V21.sKnownPropertyNameSet;
    }

    /* access modifiers changed from: protected */
    public Set<String> getKnownTypeSet() {
        return VCardParser_V21.sKnownTypeSet;
    }

    /* access modifiers changed from: protected */
    public Set<String> getKnownValueSet() {
        return VCardParser_V21.sKnownValueSet;
    }

    /* access modifiers changed from: protected */
    public Set<String> getAvailableEncodingSet() {
        return VCardParser_V21.sAvailableEncoding;
    }

    /* access modifiers changed from: protected */
    public String getDefaultEncoding() {
        return "8BIT";
    }

    /* access modifiers changed from: protected */
    public String getDefaultCharset() {
        return "UTF-8";
    }

    /* access modifiers changed from: protected */
    public String getCurrentCharset() {
        return this.mCurrentCharset;
    }

    public void addInterpreter(VCardInterpreter interpreter) {
        this.mInterpreterList.add(interpreter);
    }

    public void parse(InputStream is) throws IOException, VCardException {
        if (is != null) {
            this.mReader = new CustomBufferedReader(new InputStreamReader(is, this.mIntermediateCharset));
            System.currentTimeMillis();
            for (VCardInterpreter interpreter : this.mInterpreterList) {
                interpreter.onVCardStarted();
            }
            while (true) {
                synchronized (this) {
                    if (!this.mCanceled) {
                        if (!parseOneVCard()) {
                            break;
                        }
                    } else {
                        Log.i(LOG_TAG, "Cancel request has come. exitting parse operation.");
                        break;
                    }
                }
            }
            for (VCardInterpreter interpreter2 : this.mInterpreterList) {
                interpreter2.onVCardEnded();
            }
            return;
        }
        throw new NullPointerException("InputStream must not be null.");
    }

    public void parseOne(InputStream is) throws IOException, VCardException {
        if (is != null) {
            this.mReader = new CustomBufferedReader(new InputStreamReader(is, this.mIntermediateCharset));
            System.currentTimeMillis();
            for (VCardInterpreter interpreter : this.mInterpreterList) {
                interpreter.onVCardStarted();
            }
            parseOneVCard();
            for (VCardInterpreter interpreter2 : this.mInterpreterList) {
                interpreter2.onVCardEnded();
            }
            return;
        }
        throw new NullPointerException("InputStream must not be null.");
    }

    public final synchronized void cancel() {
        Log.i(LOG_TAG, "ParserImpl received cancel operation.");
        this.mCanceled = true;
    }
}
