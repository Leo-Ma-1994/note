package com.goodocom.gocsdk.vcard;

import android.util.Log;
import com.goodocom.gocsdk.vcard.exception.VCardException;
import java.io.IOException;
import java.util.Set;

/* access modifiers changed from: package-private */
public class VCardParserImpl_V30 extends VCardParserImpl_V21 {
    private static final String LOG_TAG = "vCard";
    private boolean mEmittedAgentWarning = false;
    private String mPreviousLine;

    public VCardParserImpl_V30() {
    }

    public VCardParserImpl_V30(int vcardType) {
        super(vcardType);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public int getVersion() {
        return 1;
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public String getVersionString() {
        return VCardConstants.VERSION_V30;
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public String peekLine() throws IOException {
        if (this.mPreviousLine != null) {
            return this.mPreviousLine;
        }
        return this.mReader.peekLine();
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public String getLine() throws IOException {
        if (this.mPreviousLine == null) {
            return this.mReader.readLine();
        }
        String ret = this.mPreviousLine;
        this.mPreviousLine = null;
        return ret;
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public String getNonEmptyLine() throws IOException, VCardException {
        String line;
        StringBuilder builder = null;
        while (true) {
            line = this.mReader.readLine();
            if (line == null) {
                break;
            } else if (line.length() != 0) {
                if (line.charAt(0) != ' ' && line.charAt(0) != '\t') {
                    if (builder != null || this.mPreviousLine != null) {
                        break;
                    }
                    this.mPreviousLine = line;
                } else {
                    if (builder == null) {
                        builder = new StringBuilder();
                    }
                    String str = this.mPreviousLine;
                    if (str != null) {
                        builder.append(str);
                        this.mPreviousLine = null;
                    }
                    builder.append(line.substring(1));
                }
            }
        }
        String ret = null;
        if (builder != null) {
            ret = builder.toString();
        } else if (this.mPreviousLine != null) {
            ret = this.mPreviousLine;
        }
        this.mPreviousLine = line;
        if (ret != null) {
            return ret;
        }
        throw new VCardException("Reached end of buffer.");
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public boolean readBeginVCard(boolean allowGarbage) throws IOException, VCardException {
        return super.readBeginVCard(allowGarbage);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public void handleParams(VCardProperty propertyData, String params) throws VCardException {
        try {
            super.handleParams(propertyData, params);
        } catch (VCardException e) {
            String[] strArray = params.split("=", 2);
            if (strArray.length == 2) {
                handleAnyParam(propertyData, strArray[0], strArray[1]);
                return;
            }
            throw new VCardException("Unknown params value: " + params);
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public void handleAnyParam(VCardProperty propertyData, String paramName, String paramValue) {
        splitAndPutParam(propertyData, paramName, paramValue);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public void handleParamWithoutName(VCardProperty property, String paramValue) {
        handleType(property, paramValue);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public void handleType(VCardProperty property, String paramValue) {
        splitAndPutParam(property, VCardConstants.PARAM_TYPE, paramValue);
    }

    private void splitAndPutParam(VCardProperty property, String paramName, String paramValue) {
        StringBuilder builder = null;
        boolean insideDquote = false;
        int length = paramValue.length();
        for (int i = 0; i < length; i++) {
            char ch = paramValue.charAt(i);
            if (ch == '\"') {
                if (insideDquote) {
                    property.addParameter(paramName, encodeParamValue(builder.toString()));
                    builder = null;
                    insideDquote = false;
                } else {
                    if (builder != null) {
                        if (builder.length() > 0) {
                            Log.w(LOG_TAG, "Unexpected Dquote inside property.");
                        } else {
                            property.addParameter(paramName, encodeParamValue(builder.toString()));
                        }
                    }
                    insideDquote = true;
                }
            } else if (ch != ',' || insideDquote) {
                if (builder == null) {
                    builder = new StringBuilder();
                }
                builder.append(ch);
            } else if (builder == null) {
                Log.w(LOG_TAG, "Comma is used before actual string comes. (" + paramValue + ")");
            } else {
                property.addParameter(paramName, encodeParamValue(builder.toString()));
                builder = null;
            }
        }
        if (insideDquote) {
            Log.d(LOG_TAG, "Dangling Dquote.");
        }
        if (builder == null) {
            return;
        }
        if (builder.length() == 0) {
            Log.w(LOG_TAG, "Unintended behavior. We must not see empty StringBuilder at the end of parameter value parsing.");
        } else {
            property.addParameter(paramName, encodeParamValue(builder.toString()));
        }
    }

    /* access modifiers changed from: protected */
    public String encodeParamValue(String paramValue) {
        return VCardUtils.convertStringCharset(paramValue, VCardConfig.DEFAULT_INTERMEDIATE_CHARSET, "UTF-8");
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public void handleAgent(VCardProperty property) {
        if (!this.mEmittedAgentWarning) {
            Log.w(LOG_TAG, "AGENT in vCard 3.0 is not supported yet. Ignore it");
            this.mEmittedAgentWarning = true;
        }
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public String getBase64(String firstString) throws IOException, VCardException {
        return firstString;
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public String maybeUnescapeText(String text) {
        return unescapeText(text);
    }

    public static String unescapeText(String text) {
        StringBuilder builder = new StringBuilder();
        int length = text.length();
        int i = 0;
        while (i < length) {
            char ch = text.charAt(i);
            if (ch != '\\' || i >= length - 1) {
                builder.append(ch);
            } else {
                i++;
                char next_ch = text.charAt(i);
                if (next_ch == 'n' || next_ch == 'N') {
                    builder.append("\n");
                } else {
                    builder.append(next_ch);
                }
            }
            i++;
        }
        return builder.toString();
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public String maybeUnescapeCharacter(char ch) {
        return unescapeCharacter(ch);
    }

    public static String unescapeCharacter(char ch) {
        if (ch == 'n' || ch == 'N') {
            return "\n";
        }
        return String.valueOf(ch);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public Set<String> getKnownPropertyNameSet() {
        return VCardParser_V30.sKnownPropertyNameSet;
    }
}
