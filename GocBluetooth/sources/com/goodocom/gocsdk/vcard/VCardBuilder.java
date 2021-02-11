package com.goodocom.gocsdk.vcard;

import android.content.ContentValues;
import android.support.v7.widget.ActivityChooserView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.goodocom.gocsdk.vcard.VCardUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VCardBuilder {
    public static final int DEFAULT_EMAIL_TYPE = 3;
    public static final int DEFAULT_PHONE_TYPE = 1;
    public static final int DEFAULT_POSTAL_TYPE = 1;
    private static final String LOG_TAG = "vCard";
    private static final String SHIFT_JIS = "SHIFT_JIS";
    private static final String VCARD_DATA_PUBLIC = "PUBLIC";
    private static final String VCARD_DATA_SEPARATOR = ":";
    private static final String VCARD_DATA_VCARD = "VCARD";
    public static final String VCARD_END_OF_LINE = "\r\n";
    private static final String VCARD_ITEM_SEPARATOR = ";";
    private static final String VCARD_PARAM_ENCODING_BASE64_AS_B = "ENCODING=B";
    private static final String VCARD_PARAM_ENCODING_BASE64_V21 = "ENCODING=BASE64";
    private static final String VCARD_PARAM_ENCODING_QP = "ENCODING=QUOTED-PRINTABLE";
    private static final String VCARD_PARAM_EQUAL = "=";
    private static final String VCARD_PARAM_SEPARATOR = ";";
    private static final String VCARD_WS = " ";
    private static final Set<String> sAllowedAndroidPropertySet = Collections.unmodifiableSet(new HashSet(Arrays.asList("vnd.android.cursor.item/nickname", "vnd.android.cursor.item/contact_event", "vnd.android.cursor.item/relation")));
    private static final Map<Integer, Integer> sPostalTypePriorityMap = new HashMap();
    private final boolean mAppendTypeParamName;
    private StringBuilder mBuilder;
    private final String mCharset;
    private boolean mEndAppended;
    private final boolean mIsDoCoMo;
    private final boolean mIsJapaneseMobilePhone;
    private final boolean mIsV30OrV40;
    private final boolean mNeedsToConvertPhoneticString;
    private final boolean mOnlyOneNoteFieldIsAvailable;
    private final boolean mRefrainsQPToNameProperties;
    private final boolean mShouldAppendCharsetParam;
    private final boolean mShouldUseQuotedPrintable;
    private final boolean mUsesAndroidProperty;
    private final boolean mUsesDefactProperty;
    private final String mVCardCharsetParameter;
    private final int mVCardType;

    static {
        sPostalTypePriorityMap.put(1, 0);
        sPostalTypePriorityMap.put(2, 1);
        sPostalTypePriorityMap.put(3, 2);
        sPostalTypePriorityMap.put(0, 3);
    }

    public VCardBuilder(int vcardType) {
        this(vcardType, null);
    }

    public VCardBuilder(int vcardType, String charset) {
        this.mVCardType = vcardType;
        if (VCardConfig.isVersion40(vcardType)) {
            Log.w(LOG_TAG, "Should not use vCard 4.0 when building vCard. It is not officially published yet.");
        }
        boolean z = false;
        this.mIsV30OrV40 = VCardConfig.isVersion30(vcardType) || VCardConfig.isVersion40(vcardType);
        this.mShouldUseQuotedPrintable = VCardConfig.shouldUseQuotedPrintable(vcardType);
        this.mIsDoCoMo = VCardConfig.isDoCoMo(vcardType);
        this.mIsJapaneseMobilePhone = VCardConfig.needsToConvertPhoneticString(vcardType);
        this.mOnlyOneNoteFieldIsAvailable = VCardConfig.onlyOneNoteFieldIsAvailable(vcardType);
        this.mUsesAndroidProperty = VCardConfig.usesAndroidSpecificProperty(vcardType);
        this.mUsesDefactProperty = VCardConfig.usesDefactProperty(vcardType);
        this.mRefrainsQPToNameProperties = VCardConfig.shouldRefrainQPToNameProperties(vcardType);
        this.mAppendTypeParamName = VCardConfig.appendTypeParamName(vcardType);
        this.mNeedsToConvertPhoneticString = VCardConfig.needsToConvertPhoneticString(vcardType);
        this.mShouldAppendCharsetParam = (!VCardConfig.isVersion30(vcardType) || !"UTF-8".equalsIgnoreCase(charset)) ? true : z;
        if (VCardConfig.isDoCoMo(vcardType)) {
            if (SHIFT_JIS.equalsIgnoreCase(charset)) {
                this.mCharset = charset;
            } else if (TextUtils.isEmpty(charset)) {
                this.mCharset = SHIFT_JIS;
            } else {
                this.mCharset = charset;
            }
            this.mVCardCharsetParameter = "CHARSET=SHIFT_JIS";
        } else if (TextUtils.isEmpty(charset)) {
            Log.i(LOG_TAG, "Use the charset \"UTF-8\" for export.");
            this.mCharset = "UTF-8";
            this.mVCardCharsetParameter = "CHARSET=UTF-8";
        } else {
            this.mCharset = charset;
            this.mVCardCharsetParameter = "CHARSET=" + charset;
        }
        clear();
    }

    public void clear() {
        this.mBuilder = new StringBuilder();
        this.mEndAppended = false;
        appendLine(VCardConstants.PROPERTY_BEGIN, VCARD_DATA_VCARD);
        if (VCardConfig.isVersion40(this.mVCardType)) {
            appendLine(VCardConstants.PROPERTY_VERSION, VCardConstants.VERSION_V40);
        } else if (VCardConfig.isVersion30(this.mVCardType)) {
            appendLine(VCardConstants.PROPERTY_VERSION, VCardConstants.VERSION_V30);
        } else {
            if (!VCardConfig.isVersion21(this.mVCardType)) {
                Log.w(LOG_TAG, "Unknown vCard version detected.");
            }
            appendLine(VCardConstants.PROPERTY_VERSION, VCardConstants.VERSION_V21);
        }
    }

    private boolean containsNonEmptyName(ContentValues contentValues) {
        return !TextUtils.isEmpty(contentValues.getAsString("data3")) || !TextUtils.isEmpty(contentValues.getAsString("data5")) || !TextUtils.isEmpty(contentValues.getAsString("data2")) || !TextUtils.isEmpty(contentValues.getAsString("data4")) || !TextUtils.isEmpty(contentValues.getAsString("data6")) || !TextUtils.isEmpty(contentValues.getAsString("data9")) || !TextUtils.isEmpty(contentValues.getAsString("data8")) || !TextUtils.isEmpty(contentValues.getAsString("data7")) || !TextUtils.isEmpty(contentValues.getAsString("data1"));
    }

    private ContentValues getPrimaryContentValueWithStructuredName(List<ContentValues> contentValuesList) {
        ContentValues primaryContentValues = null;
        ContentValues subprimaryContentValues = null;
        Iterator<ContentValues> it = contentValuesList.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ContentValues contentValues = it.next();
            if (contentValues != null) {
                Integer isSuperPrimary = contentValues.getAsInteger("is_super_primary");
                if (isSuperPrimary != null && isSuperPrimary.intValue() > 0) {
                    primaryContentValues = contentValues;
                    break;
                } else if (primaryContentValues == null) {
                    Integer isPrimary = contentValues.getAsInteger("is_primary");
                    if (isPrimary != null && isPrimary.intValue() > 0 && containsNonEmptyName(contentValues)) {
                        primaryContentValues = contentValues;
                    } else if (subprimaryContentValues == null && containsNonEmptyName(contentValues)) {
                        subprimaryContentValues = contentValues;
                    }
                }
            }
        }
        if (primaryContentValues != null) {
            return primaryContentValues;
        }
        if (subprimaryContentValues != null) {
            return subprimaryContentValues;
        }
        return new ContentValues();
    }

    private VCardBuilder appendNamePropertiesV40(List<ContentValues> contentValuesList) {
        String str;
        String givenName;
        if (this.mIsDoCoMo || this.mNeedsToConvertPhoneticString) {
            Log.w(LOG_TAG, "Invalid flag is used in vCard 4.0 construction. Ignored.");
        }
        if (contentValuesList == null) {
            str = VCardConstants.PROPERTY_FN;
        } else if (contentValuesList.isEmpty()) {
            str = VCardConstants.PROPERTY_FN;
        } else {
            ContentValues contentValues = getPrimaryContentValueWithStructuredName(contentValuesList);
            String familyName = contentValues.getAsString("data3");
            String middleName = contentValues.getAsString("data5");
            String givenName2 = contentValues.getAsString("data2");
            String prefix = contentValues.getAsString("data4");
            String suffix = contentValues.getAsString("data6");
            String formattedName = contentValues.getAsString("data1");
            if (TextUtils.isEmpty(familyName) && TextUtils.isEmpty(givenName2) && TextUtils.isEmpty(middleName) && TextUtils.isEmpty(prefix) && TextUtils.isEmpty(suffix)) {
                if (TextUtils.isEmpty(formattedName)) {
                    appendLine(VCardConstants.PROPERTY_FN, "");
                    return this;
                }
                familyName = formattedName;
            }
            String phoneticFamilyName = contentValues.getAsString("data9");
            String phoneticMiddleName = contentValues.getAsString("data8");
            String phoneticGivenName = contentValues.getAsString("data7");
            String escapedFamily = escapeCharacters(familyName);
            String escapedGiven = escapeCharacters(givenName2);
            String escapedMiddle = escapeCharacters(middleName);
            String escapedPrefix = escapeCharacters(prefix);
            String escapedSuffix = escapeCharacters(suffix);
            this.mBuilder.append(VCardConstants.PROPERTY_N);
            if (!TextUtils.isEmpty(phoneticFamilyName) || !TextUtils.isEmpty(phoneticMiddleName) || !TextUtils.isEmpty(phoneticGivenName)) {
                this.mBuilder.append(";");
                StringBuilder sb = new StringBuilder();
                givenName = givenName2;
                sb.append(escapeCharacters(phoneticFamilyName));
                sb.append(';');
                sb.append(escapeCharacters(phoneticGivenName));
                sb.append(';');
                sb.append(escapeCharacters(phoneticMiddleName));
                String sortAs = sb.toString();
                StringBuilder sb2 = this.mBuilder;
                sb2.append("SORT-AS=");
                sb2.append(VCardUtils.toStringAsV40ParamValue(sortAs));
            } else {
                givenName = givenName2;
            }
            this.mBuilder.append(VCARD_DATA_SEPARATOR);
            this.mBuilder.append(escapedFamily);
            this.mBuilder.append(";");
            this.mBuilder.append(escapedGiven);
            this.mBuilder.append(";");
            this.mBuilder.append(escapedMiddle);
            this.mBuilder.append(";");
            this.mBuilder.append(escapedPrefix);
            this.mBuilder.append(";");
            this.mBuilder.append(escapedSuffix);
            this.mBuilder.append(VCARD_END_OF_LINE);
            if (TextUtils.isEmpty(formattedName)) {
                Log.w(LOG_TAG, "DISPLAY_NAME is empty.");
                appendLine(VCardConstants.PROPERTY_FN, escapeCharacters(VCardUtils.constructNameFromElements(VCardConfig.getNameOrderType(this.mVCardType), familyName, middleName, givenName, prefix, suffix)));
            } else {
                String escapedFormatted = escapeCharacters(formattedName);
                this.mBuilder.append(VCardConstants.PROPERTY_FN);
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                this.mBuilder.append(escapedFormatted);
                this.mBuilder.append(VCARD_END_OF_LINE);
            }
            appendPhoneticNameFields(contentValues);
            return this;
        }
        appendLine(str, "");
        return this;
    }

    public VCardBuilder appendNameProperties(List<ContentValues> contentValuesList) {
        ContentValues contentValues;
        String str;
        String str2;
        String formattedName;
        int i;
        String encodedPrefix;
        String encodedMiddle;
        String encodedGiven;
        String encodedFamily;
        String familyName;
        String prefix;
        if (VCardConfig.isVersion40(this.mVCardType)) {
            return appendNamePropertiesV40(contentValuesList);
        }
        if (contentValuesList == null || contentValuesList.isEmpty()) {
            if (VCardConfig.isVersion30(this.mVCardType)) {
                appendLine(VCardConstants.PROPERTY_N, "");
                appendLine(VCardConstants.PROPERTY_FN, "");
            } else if (this.mIsDoCoMo) {
                appendLine(VCardConstants.PROPERTY_N, "");
            }
            return this;
        }
        ContentValues contentValues2 = getPrimaryContentValueWithStructuredName(contentValuesList);
        String familyName2 = contentValues2.getAsString("data3");
        String middleName = contentValues2.getAsString("data5");
        String givenName = contentValues2.getAsString("data2");
        String prefix2 = contentValues2.getAsString("data4");
        String suffix = contentValues2.getAsString("data6");
        String displayName = contentValues2.getAsString("data1");
        if (!TextUtils.isEmpty(familyName2) || !TextUtils.isEmpty(givenName)) {
            boolean reallyUseQuotedPrintableToFN = false;
            boolean reallyAppendCharsetParameterToName = shouldAppendCharsetParam(familyName2, givenName, middleName, prefix2, suffix);
            boolean reallyUseQuotedPrintableToName = !this.mRefrainsQPToNameProperties && (!VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{familyName2}) || !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{givenName}) || !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{middleName}) || !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{prefix2}) || !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{suffix}));
            if (!TextUtils.isEmpty(displayName)) {
                formattedName = displayName;
                contentValues = contentValues2;
                str2 = ";";
                str = VCARD_END_OF_LINE;
                i = 1;
            } else {
                int nameOrderType = VCardConfig.getNameOrderType(this.mVCardType);
                contentValues = contentValues2;
                i = 1;
                str2 = ";";
                str = VCARD_END_OF_LINE;
                formattedName = VCardUtils.constructNameFromElements(nameOrderType, familyName2, middleName, givenName, prefix2, suffix);
            }
            String[] strArr = new String[i];
            strArr[0] = formattedName;
            boolean reallyAppendCharsetParameterToFN = shouldAppendCharsetParam(strArr);
            if (!this.mRefrainsQPToNameProperties) {
                String[] strArr2 = new String[i];
                strArr2[0] = formattedName;
                if (!VCardUtils.containsOnlyNonCrLfPrintableAscii(strArr2)) {
                    reallyUseQuotedPrintableToFN = true;
                }
            }
            if (reallyUseQuotedPrintableToName) {
                String encodedFamily2 = encodeQuotedPrintable(familyName2);
                String encodedGiven2 = encodeQuotedPrintable(givenName);
                encodedMiddle = encodeQuotedPrintable(middleName);
                encodedPrefix = encodeQuotedPrintable(prefix2);
                familyName = encodedGiven2;
                encodedGiven = encodedFamily2;
                encodedFamily = encodeQuotedPrintable(suffix);
            } else {
                String encodedFamily3 = escapeCharacters(familyName2);
                String encodedGiven3 = escapeCharacters(givenName);
                encodedMiddle = escapeCharacters(middleName);
                encodedPrefix = escapeCharacters(prefix2);
                familyName = encodedGiven3;
                encodedGiven = encodedFamily3;
                encodedFamily = escapeCharacters(suffix);
            }
            String encodedFormattedname = reallyUseQuotedPrintableToFN ? encodeQuotedPrintable(formattedName) : escapeCharacters(formattedName);
            this.mBuilder.append(VCardConstants.PROPERTY_N);
            if (this.mIsDoCoMo) {
                if (reallyAppendCharsetParameterToName) {
                    prefix = str2;
                    this.mBuilder.append(prefix);
                    this.mBuilder.append(this.mVCardCharsetParameter);
                } else {
                    prefix = str2;
                }
                if (reallyUseQuotedPrintableToName) {
                    this.mBuilder.append(prefix);
                    this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
                }
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                this.mBuilder.append(formattedName);
                this.mBuilder.append(prefix);
                this.mBuilder.append(prefix);
                this.mBuilder.append(prefix);
                this.mBuilder.append(prefix);
            } else {
                prefix = str2;
                if (reallyAppendCharsetParameterToName) {
                    this.mBuilder.append(prefix);
                    this.mBuilder.append(this.mVCardCharsetParameter);
                }
                if (reallyUseQuotedPrintableToName) {
                    this.mBuilder.append(prefix);
                    this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
                }
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                this.mBuilder.append(encodedGiven);
                this.mBuilder.append(prefix);
                this.mBuilder.append(familyName);
                this.mBuilder.append(prefix);
                this.mBuilder.append(encodedMiddle);
                this.mBuilder.append(prefix);
                this.mBuilder.append(encodedPrefix);
                this.mBuilder.append(prefix);
                this.mBuilder.append(encodedFamily);
            }
            this.mBuilder.append(str);
            this.mBuilder.append(VCardConstants.PROPERTY_FN);
            if (reallyAppendCharsetParameterToFN) {
                this.mBuilder.append(prefix);
                this.mBuilder.append(this.mVCardCharsetParameter);
            }
            if (reallyUseQuotedPrintableToFN) {
                this.mBuilder.append(prefix);
                this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
            }
            this.mBuilder.append(VCARD_DATA_SEPARATOR);
            this.mBuilder.append(encodedFormattedname);
            this.mBuilder.append(str);
        } else if (!TextUtils.isEmpty(displayName)) {
            buildSinglePartNameField(VCardConstants.PROPERTY_N, displayName);
            this.mBuilder.append(";");
            this.mBuilder.append(";");
            this.mBuilder.append(";");
            this.mBuilder.append(";");
            this.mBuilder.append(VCARD_END_OF_LINE);
            buildSinglePartNameField(VCardConstants.PROPERTY_FN, displayName);
            this.mBuilder.append(VCARD_END_OF_LINE);
            contentValues = contentValues2;
        } else if (VCardConfig.isVersion30(this.mVCardType)) {
            appendLine(VCardConstants.PROPERTY_N, "");
            appendLine(VCardConstants.PROPERTY_FN, "");
            contentValues = contentValues2;
        } else if (this.mIsDoCoMo) {
            appendLine(VCardConstants.PROPERTY_N, "");
            contentValues = contentValues2;
        } else {
            contentValues = contentValues2;
        }
        appendPhoneticNameFields(contentValues);
        return this;
    }

    private void buildSinglePartNameField(String property, String part) {
        String encodedPart;
        boolean reallyUseQuotedPrintable = !this.mRefrainsQPToNameProperties && !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{part});
        if (reallyUseQuotedPrintable) {
            encodedPart = encodeQuotedPrintable(part);
        } else {
            encodedPart = escapeCharacters(part);
        }
        this.mBuilder.append(property);
        if (shouldAppendCharsetParam(part)) {
            this.mBuilder.append(";");
            this.mBuilder.append(this.mVCardCharsetParameter);
        }
        if (reallyUseQuotedPrintable) {
            this.mBuilder.append(";");
            this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
        }
        this.mBuilder.append(VCARD_DATA_SEPARATOR);
        this.mBuilder.append(encodedPart);
    }

    private void appendPhoneticNameFields(ContentValues contentValues) {
        String phoneticGivenName;
        String phoneticMiddleName;
        String phoneticFamilyName;
        String encodedPhoneticFamilyName;
        String encodedPhoneticMiddleName;
        String encodedPhoneticGivenName;
        String encodedPhoneticGivenName2;
        String encodedPhoneticMiddleName2;
        String encodedPhoneticFamilyName2;
        String tmpPhoneticFamilyName = contentValues.getAsString("data9");
        String tmpPhoneticMiddleName = contentValues.getAsString("data8");
        String tmpPhoneticGivenName = contentValues.getAsString("data7");
        if (this.mNeedsToConvertPhoneticString) {
            phoneticFamilyName = VCardUtils.toHalfWidthString(tmpPhoneticFamilyName);
            phoneticMiddleName = VCardUtils.toHalfWidthString(tmpPhoneticMiddleName);
            phoneticGivenName = VCardUtils.toHalfWidthString(tmpPhoneticGivenName);
        } else {
            phoneticFamilyName = tmpPhoneticFamilyName;
            phoneticMiddleName = tmpPhoneticMiddleName;
            phoneticGivenName = tmpPhoneticGivenName;
        }
        if (!TextUtils.isEmpty(phoneticFamilyName) || !TextUtils.isEmpty(phoneticMiddleName) || !TextUtils.isEmpty(phoneticGivenName)) {
            if (!VCardConfig.isVersion40(this.mVCardType)) {
                if (VCardConfig.isVersion30(this.mVCardType)) {
                    String sortString = VCardUtils.constructNameFromElements(this.mVCardType, phoneticFamilyName, phoneticMiddleName, phoneticGivenName);
                    this.mBuilder.append(VCardConstants.PROPERTY_SORT_STRING);
                    if (VCardConfig.isVersion30(this.mVCardType) && shouldAppendCharsetParam(sortString)) {
                        this.mBuilder.append(";");
                        this.mBuilder.append(this.mVCardCharsetParameter);
                    }
                    this.mBuilder.append(VCARD_DATA_SEPARATOR);
                    this.mBuilder.append(escapeCharacters(sortString));
                    this.mBuilder.append(VCARD_END_OF_LINE);
                } else if (this.mIsJapaneseMobilePhone) {
                    this.mBuilder.append(VCardConstants.PROPERTY_SOUND);
                    this.mBuilder.append(";");
                    this.mBuilder.append("X-IRMC-N");
                    if (!this.mRefrainsQPToNameProperties && (!VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{phoneticFamilyName}) || !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{phoneticMiddleName}) || !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{phoneticGivenName}))) {
                        encodedPhoneticFamilyName2 = encodeQuotedPrintable(phoneticFamilyName);
                        encodedPhoneticMiddleName2 = encodeQuotedPrintable(phoneticMiddleName);
                        encodedPhoneticGivenName2 = encodeQuotedPrintable(phoneticGivenName);
                    } else {
                        encodedPhoneticFamilyName2 = escapeCharacters(phoneticFamilyName);
                        encodedPhoneticMiddleName2 = escapeCharacters(phoneticMiddleName);
                        encodedPhoneticGivenName2 = escapeCharacters(phoneticGivenName);
                    }
                    if (shouldAppendCharsetParam(encodedPhoneticFamilyName2, encodedPhoneticMiddleName2, encodedPhoneticGivenName2)) {
                        this.mBuilder.append(";");
                        this.mBuilder.append(this.mVCardCharsetParameter);
                    }
                    this.mBuilder.append(VCARD_DATA_SEPARATOR);
                    boolean first = true;
                    if (!TextUtils.isEmpty(encodedPhoneticFamilyName2)) {
                        this.mBuilder.append(encodedPhoneticFamilyName2);
                        first = false;
                    }
                    if (!TextUtils.isEmpty(encodedPhoneticMiddleName2)) {
                        if (first) {
                            first = false;
                        } else {
                            this.mBuilder.append(' ');
                        }
                        this.mBuilder.append(encodedPhoneticMiddleName2);
                    }
                    if (!TextUtils.isEmpty(encodedPhoneticGivenName2)) {
                        if (!first) {
                            this.mBuilder.append(' ');
                        }
                        this.mBuilder.append(encodedPhoneticGivenName2);
                    }
                    this.mBuilder.append(";");
                    this.mBuilder.append(";");
                    this.mBuilder.append(";");
                    this.mBuilder.append(";");
                    this.mBuilder.append(VCARD_END_OF_LINE);
                }
            }
            if (this.mUsesDefactProperty) {
                if (!TextUtils.isEmpty(phoneticGivenName)) {
                    boolean reallyUseQuotedPrintable = this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{phoneticGivenName});
                    if (reallyUseQuotedPrintable) {
                        encodedPhoneticGivenName = encodeQuotedPrintable(phoneticGivenName);
                    } else {
                        encodedPhoneticGivenName = escapeCharacters(phoneticGivenName);
                    }
                    this.mBuilder.append(VCardConstants.PROPERTY_X_PHONETIC_FIRST_NAME);
                    if (shouldAppendCharsetParam(phoneticGivenName)) {
                        this.mBuilder.append(";");
                        this.mBuilder.append(this.mVCardCharsetParameter);
                    }
                    if (reallyUseQuotedPrintable) {
                        this.mBuilder.append(";");
                        this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
                    }
                    this.mBuilder.append(VCARD_DATA_SEPARATOR);
                    this.mBuilder.append(encodedPhoneticGivenName);
                    this.mBuilder.append(VCARD_END_OF_LINE);
                }
                if (!TextUtils.isEmpty(phoneticMiddleName)) {
                    boolean reallyUseQuotedPrintable2 = this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{phoneticMiddleName});
                    if (reallyUseQuotedPrintable2) {
                        encodedPhoneticMiddleName = encodeQuotedPrintable(phoneticMiddleName);
                    } else {
                        encodedPhoneticMiddleName = escapeCharacters(phoneticMiddleName);
                    }
                    this.mBuilder.append(VCardConstants.PROPERTY_X_PHONETIC_MIDDLE_NAME);
                    if (shouldAppendCharsetParam(phoneticMiddleName)) {
                        this.mBuilder.append(";");
                        this.mBuilder.append(this.mVCardCharsetParameter);
                    }
                    if (reallyUseQuotedPrintable2) {
                        this.mBuilder.append(";");
                        this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
                    }
                    this.mBuilder.append(VCARD_DATA_SEPARATOR);
                    this.mBuilder.append(encodedPhoneticMiddleName);
                    this.mBuilder.append(VCARD_END_OF_LINE);
                }
                if (!TextUtils.isEmpty(phoneticFamilyName)) {
                    boolean reallyUseQuotedPrintable3 = this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{phoneticFamilyName});
                    if (reallyUseQuotedPrintable3) {
                        encodedPhoneticFamilyName = encodeQuotedPrintable(phoneticFamilyName);
                    } else {
                        encodedPhoneticFamilyName = escapeCharacters(phoneticFamilyName);
                    }
                    this.mBuilder.append(VCardConstants.PROPERTY_X_PHONETIC_LAST_NAME);
                    if (shouldAppendCharsetParam(phoneticFamilyName)) {
                        this.mBuilder.append(";");
                        this.mBuilder.append(this.mVCardCharsetParameter);
                    }
                    if (reallyUseQuotedPrintable3) {
                        this.mBuilder.append(";");
                        this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
                    }
                    this.mBuilder.append(VCARD_DATA_SEPARATOR);
                    this.mBuilder.append(encodedPhoneticFamilyName);
                    this.mBuilder.append(VCARD_END_OF_LINE);
                }
            }
        } else if (this.mIsDoCoMo) {
            this.mBuilder.append(VCardConstants.PROPERTY_SOUND);
            this.mBuilder.append(";");
            this.mBuilder.append("X-IRMC-N");
            this.mBuilder.append(VCARD_DATA_SEPARATOR);
            this.mBuilder.append(";");
            this.mBuilder.append(";");
            this.mBuilder.append(";");
            this.mBuilder.append(";");
            this.mBuilder.append(VCARD_END_OF_LINE);
        }
    }

    public VCardBuilder appendNickNames(List<ContentValues> contentValuesList) {
        boolean useAndroidProperty;
        if (this.mIsV30OrV40) {
            useAndroidProperty = false;
        } else if (!this.mUsesAndroidProperty) {
            return this;
        } else {
            useAndroidProperty = true;
        }
        if (contentValuesList != null) {
            for (ContentValues contentValues : contentValuesList) {
                String nickname = contentValues.getAsString("data1");
                if (!TextUtils.isEmpty(nickname)) {
                    if (useAndroidProperty) {
                        appendAndroidSpecificProperty("vnd.android.cursor.item/nickname", contentValues);
                    } else {
                        appendLineWithCharsetAndQPDetection(VCardConstants.PROPERTY_NICKNAME, nickname);
                    }
                }
            }
        }
        return this;
    }

    public VCardBuilder appendPhones(List<ContentValues> contentValuesList, VCardPhoneNumberTranslationCallback translationCallback) {
        Iterator<ContentValues> it;
        ContentValues contentValues;
        Iterator<ContentValues> it2;
        boolean phoneLineExists;
        String formatted;
        ContentValues contentValues2;
        VCardPhoneNumberTranslationCallback vCardPhoneNumberTranslationCallback = translationCallback;
        boolean phoneLineExists2 = false;
        if (contentValuesList != null) {
            Set<String> phoneSet = new HashSet<>();
            Iterator<ContentValues> it3 = contentValuesList.iterator();
            while (it3.hasNext()) {
                ContentValues contentValues3 = it3.next();
                Integer typeAsObject = contentValues3.getAsInteger("data2");
                String label = contentValues3.getAsString("data3");
                Integer isPrimaryAsInteger = contentValues3.getAsInteger("is_primary");
                boolean isPrimary = isPrimaryAsInteger != null && isPrimaryAsInteger.intValue() > 0;
                String phoneNumber = contentValues3.getAsString("data1");
                if (phoneNumber != null) {
                    phoneNumber = phoneNumber.trim();
                }
                if (!TextUtils.isEmpty(phoneNumber)) {
                    int type = typeAsObject != null ? typeAsObject.intValue() : 1;
                    if (vCardPhoneNumberTranslationCallback != null) {
                        String phoneNumber2 = vCardPhoneNumberTranslationCallback.onValueReceived(phoneNumber, type, label, isPrimary);
                        if (!phoneSet.contains(phoneNumber2)) {
                            phoneSet.add(phoneNumber2);
                            appendTelLine(Integer.valueOf(type), label, phoneNumber2, isPrimary);
                        }
                        it = it3;
                    } else {
                        if (type == 6) {
                            it = it3;
                        } else if (VCardConfig.refrainPhoneNumberFormatting(this.mVCardType)) {
                            it = it3;
                        } else {
                            List<String> phoneNumberList = splitPhoneNumbers(phoneNumber);
                            if (!phoneNumberList.isEmpty()) {
                                phoneLineExists2 = true;
                                for (String actualPhoneNumber : phoneNumberList) {
                                    if (!phoneSet.contains(actualPhoneNumber)) {
                                        phoneLineExists = phoneLineExists2;
                                        String numberWithControlSequence = actualPhoneNumber.replace(',', 'p').replace(';', 'w');
                                        if (TextUtils.equals(numberWithControlSequence, actualPhoneNumber)) {
                                            StringBuilder digitsOnlyBuilder = new StringBuilder();
                                            int length = actualPhoneNumber.length();
                                            it2 = it3;
                                            int i = 0;
                                            while (i < length) {
                                                char ch = actualPhoneNumber.charAt(i);
                                                if (!Character.isDigit(ch)) {
                                                    contentValues2 = contentValues3;
                                                    if (ch != '+') {
                                                        i++;
                                                        length = length;
                                                        contentValues3 = contentValues2;
                                                    }
                                                } else {
                                                    contentValues2 = contentValues3;
                                                }
                                                digitsOnlyBuilder.append(ch);
                                                i++;
                                                length = length;
                                                contentValues3 = contentValues2;
                                            }
                                            contentValues = contentValues3;
                                            formatted = VCardUtils.PhoneNumberUtilsPort.formatNumber(digitsOnlyBuilder.toString(), VCardUtils.getPhoneNumberFormat(this.mVCardType));
                                        } else {
                                            it2 = it3;
                                            contentValues = contentValues3;
                                            formatted = numberWithControlSequence;
                                        }
                                        if (VCardConfig.isVersion40(this.mVCardType) && !TextUtils.isEmpty(formatted) && !formatted.startsWith("tel:")) {
                                            formatted = "tel:" + formatted;
                                        }
                                        phoneSet.add(actualPhoneNumber);
                                        appendTelLine(Integer.valueOf(type), label, formatted, isPrimary);
                                    } else {
                                        phoneLineExists = phoneLineExists2;
                                        it2 = it3;
                                        contentValues = contentValues3;
                                    }
                                    phoneLineExists2 = phoneLineExists;
                                    it3 = it2;
                                    contentValues3 = contentValues;
                                }
                                it = it3;
                            }
                        }
                        phoneLineExists2 = true;
                        if (!phoneSet.contains(phoneNumber)) {
                            phoneSet.add(phoneNumber);
                            appendTelLine(Integer.valueOf(type), label, phoneNumber, isPrimary);
                        }
                    }
                    vCardPhoneNumberTranslationCallback = translationCallback;
                    it3 = it;
                }
            }
        }
        if (!phoneLineExists2 && this.mIsDoCoMo) {
            appendTelLine(1, "", "", false);
        }
        return this;
    }

    private List<String> splitPhoneNumbers(String phoneNumber) {
        List<String> phoneList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        int length = phoneNumber.length();
        for (int i = 0; i < length; i++) {
            char ch = phoneNumber.charAt(i);
            if (ch != '\n' || builder.length() <= 0) {
                builder.append(ch);
            } else {
                phoneList.add(builder.toString());
                builder = new StringBuilder();
            }
        }
        if (builder.length() > 0) {
            phoneList.add(builder.toString());
        }
        return phoneList;
    }

    public VCardBuilder appendEmails(List<ContentValues> contentValuesList) {
        boolean emailAddressExists = false;
        if (contentValuesList != null) {
            Set<String> addressSet = new HashSet<>();
            for (ContentValues contentValues : contentValuesList) {
                String emailAddress = contentValues.getAsString("data1");
                if (emailAddress != null) {
                    emailAddress = emailAddress.trim();
                }
                if (!TextUtils.isEmpty(emailAddress)) {
                    Integer typeAsObject = contentValues.getAsInteger("data2");
                    int type = typeAsObject != null ? typeAsObject.intValue() : 3;
                    String label = contentValues.getAsString("data3");
                    Integer isPrimaryAsInteger = contentValues.getAsInteger("is_primary");
                    boolean isPrimary = isPrimaryAsInteger != null && isPrimaryAsInteger.intValue() > 0;
                    emailAddressExists = true;
                    if (!addressSet.contains(emailAddress)) {
                        addressSet.add(emailAddress);
                        appendEmailLine(type, label, emailAddress, isPrimary);
                    }
                }
            }
        }
        if (!emailAddressExists && this.mIsDoCoMo) {
            appendEmailLine(1, "", "", false);
        }
        return this;
    }

    public VCardBuilder appendPostals(List<ContentValues> contentValuesList) {
        if (contentValuesList == null || contentValuesList.isEmpty()) {
            if (this.mIsDoCoMo) {
                this.mBuilder.append(VCardConstants.PROPERTY_ADR);
                this.mBuilder.append(";");
                this.mBuilder.append(VCardConstants.PARAM_TYPE_HOME);
                this.mBuilder.append(VCARD_DATA_SEPARATOR);
                this.mBuilder.append(VCARD_END_OF_LINE);
            }
        } else if (this.mIsDoCoMo) {
            appendPostalsForDoCoMo(contentValuesList);
        } else {
            appendPostalsForGeneric(contentValuesList);
        }
        return this;
    }

    private void appendPostalsForDoCoMo(List<ContentValues> contentValuesList) {
        int currentPriority = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        int currentType = ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        ContentValues currentContentValues = null;
        for (ContentValues contentValues : contentValuesList) {
            if (contentValues != null) {
                Integer typeAsInteger = contentValues.getAsInteger("data2");
                Integer priorityAsInteger = sPostalTypePriorityMap.get(typeAsInteger);
                int priority = priorityAsInteger != null ? priorityAsInteger.intValue() : ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
                if (priority < currentPriority) {
                    currentPriority = priority;
                    currentType = typeAsInteger.intValue();
                    currentContentValues = contentValues;
                    if (priority == 0) {
                        break;
                    }
                } else {
                    continue;
                }
            }
        }
        if (currentContentValues == null) {
            Log.w(LOG_TAG, "Should not come here. Must have at least one postal data.");
        } else {
            appendPostalLine(currentType, currentContentValues.getAsString("data3"), currentContentValues, false, true);
        }
    }

    private void appendPostalsForGeneric(List<ContentValues> contentValuesList) {
        for (ContentValues contentValues : contentValuesList) {
            if (contentValues != null) {
                Integer typeAsInteger = contentValues.getAsInteger("data2");
                int type = typeAsInteger != null ? typeAsInteger.intValue() : 1;
                String label = contentValues.getAsString("data3");
                Integer isPrimaryAsInteger = contentValues.getAsInteger("is_primary");
                appendPostalLine(type, label, contentValues, isPrimaryAsInteger != null && isPrimaryAsInteger.intValue() > 0, false);
            }
        }
    }

    /* access modifiers changed from: private */
    public static class PostalStruct {
        final String addressData;
        final boolean appendCharset;
        final boolean reallyUseQuotedPrintable;

        public PostalStruct(boolean reallyUseQuotedPrintable2, boolean appendCharset2, String addressData2) {
            this.reallyUseQuotedPrintable = reallyUseQuotedPrintable2;
            this.appendCharset = appendCharset2;
            this.addressData = addressData2;
        }
    }

    private PostalStruct tryConstructPostalStruct(ContentValues contentValues) {
        String encodedFormattedAddress;
        String rawLocality2;
        String encodedStreet;
        String encodedPoBox;
        String rawLocality;
        String rawStreet;
        String rawNeighborhood;
        String rawPoBox;
        String rawPoBox2 = contentValues.getAsString("data5");
        String rawNeighborhood2 = contentValues.getAsString("data6");
        String rawStreet2 = contentValues.getAsString("data4");
        String rawLocality3 = contentValues.getAsString("data7");
        String rawRegion = contentValues.getAsString("data8");
        String rawPostalCode = contentValues.getAsString("data9");
        String rawCountry = contentValues.getAsString("data10");
        boolean reallyUseQuotedPrintable = false;
        String[] rawAddressArray = {rawPoBox2, rawNeighborhood2, rawStreet2, rawLocality3, rawRegion, rawPostalCode, rawCountry};
        if (!VCardUtils.areAllEmpty(rawAddressArray)) {
            if (this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(rawAddressArray)) {
                reallyUseQuotedPrintable = true;
            }
            boolean appendCharset = true ^ VCardUtils.containsOnlyPrintableAscii(rawAddressArray);
            if (TextUtils.isEmpty(rawLocality3)) {
                if (TextUtils.isEmpty(rawNeighborhood2)) {
                    rawLocality2 = "";
                } else {
                    rawLocality2 = rawNeighborhood2;
                }
            } else if (TextUtils.isEmpty(rawNeighborhood2)) {
                rawLocality2 = rawLocality3;
            } else {
                rawLocality2 = rawLocality3 + VCARD_WS + rawNeighborhood2;
            }
            if (reallyUseQuotedPrintable) {
                encodedPoBox = encodeQuotedPrintable(rawPoBox2);
                encodedStreet = encodeQuotedPrintable(rawStreet2);
                rawPoBox = encodeQuotedPrintable(rawLocality2);
                rawNeighborhood = encodeQuotedPrintable(rawRegion);
                rawStreet = encodeQuotedPrintable(rawPostalCode);
                rawLocality = encodeQuotedPrintable(rawCountry);
            } else {
                encodedPoBox = escapeCharacters(rawPoBox2);
                encodedStreet = escapeCharacters(rawStreet2);
                String encodedLocality = escapeCharacters(rawLocality2);
                String encodedRegion = escapeCharacters(rawRegion);
                String encodedPostalCode = escapeCharacters(rawPostalCode);
                String encodedCountry = escapeCharacters(rawCountry);
                escapeCharacters(rawNeighborhood2);
                rawPoBox = encodedLocality;
                rawNeighborhood = encodedRegion;
                rawStreet = encodedPostalCode;
                rawLocality = encodedCountry;
            }
            return new PostalStruct(reallyUseQuotedPrintable, appendCharset, encodedPoBox + ";;" + encodedStreet + ";" + rawPoBox + ";" + rawNeighborhood + ";" + rawStreet + ";" + rawLocality);
        }
        String rawFormattedAddress = contentValues.getAsString("data1");
        if (TextUtils.isEmpty(rawFormattedAddress)) {
            return null;
        }
        boolean reallyUseQuotedPrintable2 = this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{rawFormattedAddress});
        boolean appendCharset2 = !VCardUtils.containsOnlyPrintableAscii(rawFormattedAddress);
        if (reallyUseQuotedPrintable2) {
            encodedFormattedAddress = encodeQuotedPrintable(rawFormattedAddress);
        } else {
            encodedFormattedAddress = escapeCharacters(rawFormattedAddress);
        }
        return new PostalStruct(reallyUseQuotedPrintable2, appendCharset2, ";" + encodedFormattedAddress + ";;;;;");
    }

    public VCardBuilder appendIms(List<ContentValues> contentValuesList) {
        String propertyName;
        String label;
        if (contentValuesList != null) {
            for (ContentValues contentValues : contentValuesList) {
                Integer protocolAsObject = contentValues.getAsInteger("data5");
                if (!(protocolAsObject == null || (propertyName = VCardUtils.getPropertyNameForIm(protocolAsObject.intValue())) == null)) {
                    String data = contentValues.getAsString("data1");
                    if (data != null) {
                        data = data.trim();
                    }
                    if (!TextUtils.isEmpty(data)) {
                        Integer typeAsInteger = contentValues.getAsInteger("data2");
                        int intValue = typeAsInteger != null ? typeAsInteger.intValue() : 3;
                        if (intValue == 0) {
                            String label2 = contentValues.getAsString("data3");
                            label = label2 != null ? "X-" + label2 : null;
                        } else if (intValue == 1) {
                            label = VCardConstants.PARAM_TYPE_HOME;
                        } else if (intValue != 2) {
                            label = null;
                        } else {
                            label = VCardConstants.PARAM_TYPE_WORK;
                        }
                        List<String> parameterList = new ArrayList<>();
                        if (!TextUtils.isEmpty(label)) {
                            parameterList.add(label);
                        }
                        Integer isPrimaryAsInteger = contentValues.getAsInteger("is_primary");
                        boolean isPrimary = false;
                        if (isPrimaryAsInteger != null && isPrimaryAsInteger.intValue() > 0) {
                            isPrimary = true;
                        }
                        if (isPrimary) {
                            parameterList.add(VCardConstants.PARAM_TYPE_PREF);
                        }
                        appendLineWithCharsetAndQPDetection(propertyName, parameterList, data);
                    }
                }
            }
        }
        return this;
    }

    public VCardBuilder appendWebsites(List<ContentValues> contentValuesList) {
        if (contentValuesList != null) {
            for (ContentValues contentValues : contentValuesList) {
                String website = contentValues.getAsString("data1");
                if (website != null) {
                    website = website.trim();
                }
                if (!TextUtils.isEmpty(website)) {
                    appendLineWithCharsetAndQPDetection(VCardConstants.PROPERTY_URL, website);
                }
            }
        }
        return this;
    }

    public VCardBuilder appendOrganizations(List<ContentValues> contentValuesList) {
        if (contentValuesList != null) {
            for (ContentValues contentValues : contentValuesList) {
                String company = contentValues.getAsString("data1");
                if (company != null) {
                    company = company.trim();
                }
                String department = contentValues.getAsString("data5");
                if (department != null) {
                    department = department.trim();
                }
                String title = contentValues.getAsString("data4");
                if (title != null) {
                    title = title.trim();
                }
                StringBuilder orgBuilder = new StringBuilder();
                if (!TextUtils.isEmpty(company)) {
                    orgBuilder.append(company);
                }
                if (!TextUtils.isEmpty(department)) {
                    if (orgBuilder.length() > 0) {
                        orgBuilder.append(';');
                    }
                    orgBuilder.append(department);
                }
                String orgline = orgBuilder.toString();
                boolean z = true;
                appendLine(VCardConstants.PROPERTY_ORG, orgline, !VCardUtils.containsOnlyPrintableAscii(orgline), this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{orgline}));
                if (!TextUtils.isEmpty(title)) {
                    boolean z2 = !VCardUtils.containsOnlyPrintableAscii(title);
                    if (!this.mShouldUseQuotedPrintable || VCardUtils.containsOnlyNonCrLfPrintableAscii(title)) {
                        z = false;
                    }
                    appendLine(VCardConstants.PROPERTY_TITLE, title, z2, z);
                }
            }
        }
        return this;
    }

    public VCardBuilder appendPhotos(List<ContentValues> contentValuesList) {
        byte[] data;
        if (contentValuesList != null) {
            for (ContentValues contentValues : contentValuesList) {
                if (!(contentValues == null || (data = contentValues.getAsByteArray("data15")) == null)) {
                    String photoType = VCardUtils.guessImageType(data);
                    if (photoType == null) {
                        Log.d(LOG_TAG, "Unknown photo type. Ignored.");
                    } else {
                        String photoString = new String(Base64.encode(data, 2));
                        if (!TextUtils.isEmpty(photoString)) {
                            appendPhotoLine(photoString, photoType);
                        }
                    }
                }
            }
        }
        return this;
    }

    public VCardBuilder appendNotes(List<ContentValues> contentValuesList) {
        if (contentValuesList != null) {
            boolean reallyUseQuotedPrintable = false;
            if (this.mOnlyOneNoteFieldIsAvailable) {
                StringBuilder noteBuilder = new StringBuilder();
                boolean first = true;
                for (ContentValues contentValues : contentValuesList) {
                    String note = contentValues.getAsString("data1");
                    if (note == null) {
                        note = "";
                    }
                    if (note.length() > 0) {
                        if (first) {
                            first = false;
                        } else {
                            noteBuilder.append('\n');
                        }
                        noteBuilder.append(note);
                    }
                }
                String noteStr = noteBuilder.toString();
                boolean shouldAppendCharsetInfo = !VCardUtils.containsOnlyPrintableAscii(noteStr);
                if (this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(noteStr)) {
                    reallyUseQuotedPrintable = true;
                }
                appendLine(VCardConstants.PROPERTY_NOTE, noteStr, shouldAppendCharsetInfo, reallyUseQuotedPrintable);
            } else {
                for (ContentValues contentValues2 : contentValuesList) {
                    String noteStr2 = contentValues2.getAsString("data1");
                    if (!TextUtils.isEmpty(noteStr2)) {
                        appendLine(VCardConstants.PROPERTY_NOTE, noteStr2, !VCardUtils.containsOnlyPrintableAscii(noteStr2), this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{noteStr2}));
                    }
                }
            }
        }
        return this;
    }

    public VCardBuilder appendEvents(List<ContentValues> contentValuesList) {
        int eventType;
        if (contentValuesList != null) {
            String primaryBirthday = null;
            String secondaryBirthday = null;
            Iterator<ContentValues> it = contentValuesList.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ContentValues contentValues = it.next();
                if (contentValues != null) {
                    Integer eventTypeAsInteger = contentValues.getAsInteger("data2");
                    if (eventTypeAsInteger != null) {
                        eventType = eventTypeAsInteger.intValue();
                    } else {
                        eventType = 2;
                    }
                    if (eventType == 3) {
                        String birthdayCandidate = contentValues.getAsString("data1");
                        if (birthdayCandidate == null) {
                            continue;
                        } else {
                            Integer isSuperPrimaryAsInteger = contentValues.getAsInteger("is_super_primary");
                            boolean isPrimary = false;
                            if (isSuperPrimaryAsInteger != null && isSuperPrimaryAsInteger.intValue() > 0) {
                                primaryBirthday = birthdayCandidate;
                                break;
                            }
                            Integer isPrimaryAsInteger = contentValues.getAsInteger("is_primary");
                            if (isPrimaryAsInteger != null && isPrimaryAsInteger.intValue() > 0) {
                                isPrimary = true;
                            }
                            if (isPrimary) {
                                primaryBirthday = birthdayCandidate;
                            } else if (secondaryBirthday == null) {
                                secondaryBirthday = birthdayCandidate;
                            }
                        }
                    } else if (this.mUsesAndroidProperty) {
                        appendAndroidSpecificProperty("vnd.android.cursor.item/contact_event", contentValues);
                    }
                }
            }
            if (primaryBirthday != null) {
                appendLineWithCharsetAndQPDetection(VCardConstants.PROPERTY_BDAY, primaryBirthday.trim());
            } else if (secondaryBirthday != null) {
                appendLineWithCharsetAndQPDetection(VCardConstants.PROPERTY_BDAY, secondaryBirthday.trim());
            }
        }
        return this;
    }

    public VCardBuilder appendRelation(List<ContentValues> contentValuesList) {
        if (this.mUsesAndroidProperty && contentValuesList != null) {
            for (ContentValues contentValues : contentValuesList) {
                if (contentValues != null) {
                    appendAndroidSpecificProperty("vnd.android.cursor.item/relation", contentValues);
                }
            }
        }
        return this;
    }

    public void appendPostalLine(int type, String label, ContentValues contentValues, boolean isPrimary, boolean emitEveryTime) {
        String addressValue;
        boolean appendCharset;
        boolean reallyUseQuotedPrintable;
        PostalStruct postalStruct = tryConstructPostalStruct(contentValues);
        if (postalStruct != null) {
            reallyUseQuotedPrintable = postalStruct.reallyUseQuotedPrintable;
            appendCharset = postalStruct.appendCharset;
            addressValue = postalStruct.addressData;
        } else if (emitEveryTime) {
            reallyUseQuotedPrintable = false;
            appendCharset = false;
            addressValue = "";
        } else {
            return;
        }
        List<String> parameterList = new ArrayList<>();
        if (isPrimary) {
            parameterList.add(VCardConstants.PARAM_TYPE_PREF);
        }
        if (type != 0) {
            if (type == 1) {
                parameterList.add(VCardConstants.PARAM_TYPE_HOME);
            } else if (type == 2) {
                parameterList.add(VCardConstants.PARAM_TYPE_WORK);
            } else if (type != 3) {
                Log.e(LOG_TAG, "Unknown StructuredPostal type: " + type);
            }
        } else if (!TextUtils.isEmpty(label) && VCardUtils.containsOnlyAlphaDigitHyphen(label)) {
            parameterList.add("X-" + label);
        }
        this.mBuilder.append(VCardConstants.PROPERTY_ADR);
        if (!parameterList.isEmpty()) {
            this.mBuilder.append(";");
            appendTypeParameters(parameterList);
        }
        if (appendCharset) {
            this.mBuilder.append(";");
            this.mBuilder.append(this.mVCardCharsetParameter);
        }
        if (reallyUseQuotedPrintable) {
            this.mBuilder.append(";");
            this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
        }
        this.mBuilder.append(VCARD_DATA_SEPARATOR);
        this.mBuilder.append(addressValue);
        this.mBuilder.append(VCARD_END_OF_LINE);
    }

    public void appendEmailLine(int type, String label, String rawValue, boolean isPrimary) {
        String typeAsString;
        if (type != 0) {
            if (type == 1) {
                typeAsString = VCardConstants.PARAM_TYPE_HOME;
            } else if (type == 2) {
                typeAsString = VCardConstants.PARAM_TYPE_WORK;
            } else if (type == 3) {
                typeAsString = null;
            } else if (type != 4) {
                Log.e(LOG_TAG, "Unknown Email type: " + type);
                typeAsString = null;
            } else {
                typeAsString = VCardConstants.PARAM_TYPE_CELL;
            }
        } else if (VCardUtils.isMobilePhoneLabel(label)) {
            typeAsString = VCardConstants.PARAM_TYPE_CELL;
        } else if (TextUtils.isEmpty(label) || !VCardUtils.containsOnlyAlphaDigitHyphen(label)) {
            typeAsString = null;
        } else {
            typeAsString = "X-" + label;
        }
        List<String> parameterList = new ArrayList<>();
        if (isPrimary) {
            parameterList.add(VCardConstants.PARAM_TYPE_PREF);
        }
        if (!TextUtils.isEmpty(typeAsString)) {
            parameterList.add(typeAsString);
        }
        appendLineWithCharsetAndQPDetection(VCardConstants.PROPERTY_EMAIL, parameterList, rawValue);
    }

    public void appendTelLine(Integer typeAsInteger, String label, String encodedValue, boolean isPrimary) {
        int type;
        this.mBuilder.append(VCardConstants.PROPERTY_TEL);
        this.mBuilder.append(";");
        if (typeAsInteger == null) {
            type = 7;
        } else {
            type = typeAsInteger.intValue();
        }
        ArrayList<String> parameterList = new ArrayList<>();
        switch (type) {
            case 0:
                if (!TextUtils.isEmpty(label)) {
                    if (!VCardUtils.isMobilePhoneLabel(label)) {
                        if (!this.mIsV30OrV40) {
                            String upperLabel = label.toUpperCase();
                            if (!VCardUtils.isValidInV21ButUnknownToContactsPhoteType(upperLabel)) {
                                if (VCardUtils.containsOnlyAlphaDigitHyphen(label)) {
                                    parameterList.add("X-" + label);
                                    break;
                                }
                            } else {
                                parameterList.add(upperLabel);
                                break;
                            }
                        } else {
                            parameterList.add(label);
                            break;
                        }
                    } else {
                        parameterList.add(VCardConstants.PARAM_TYPE_CELL);
                        break;
                    }
                } else {
                    parameterList.add(VCardConstants.PARAM_TYPE_VOICE);
                    break;
                }
                break;
            case 1:
                parameterList.addAll(Arrays.asList(VCardConstants.PARAM_TYPE_HOME));
                break;
            case 2:
                parameterList.add(VCardConstants.PARAM_TYPE_CELL);
                break;
            case 3:
                parameterList.addAll(Arrays.asList(VCardConstants.PARAM_TYPE_WORK));
                break;
            case 4:
                parameterList.addAll(Arrays.asList(VCardConstants.PARAM_TYPE_WORK, VCardConstants.PARAM_TYPE_FAX));
                break;
            case 5:
                parameterList.addAll(Arrays.asList(VCardConstants.PARAM_TYPE_HOME, VCardConstants.PARAM_TYPE_FAX));
                break;
            case 6:
                if (!this.mIsDoCoMo) {
                    parameterList.add(VCardConstants.PARAM_TYPE_PAGER);
                    break;
                } else {
                    parameterList.add(VCardConstants.PARAM_TYPE_VOICE);
                    break;
                }
            case 7:
                parameterList.add(VCardConstants.PARAM_TYPE_VOICE);
                break;
            case 9:
                parameterList.add(VCardConstants.PARAM_TYPE_CAR);
                break;
            case 10:
                parameterList.add(VCardConstants.PARAM_TYPE_WORK);
                isPrimary = true;
                break;
            case 11:
                parameterList.add(VCardConstants.PARAM_TYPE_ISDN);
                break;
            case 12:
                isPrimary = true;
                break;
            case 13:
                parameterList.add(VCardConstants.PARAM_TYPE_FAX);
                break;
            case 15:
                parameterList.add(VCardConstants.PARAM_TYPE_TLX);
                break;
            case 17:
                parameterList.addAll(Arrays.asList(VCardConstants.PARAM_TYPE_WORK, VCardConstants.PARAM_TYPE_CELL));
                break;
            case 18:
                parameterList.add(VCardConstants.PARAM_TYPE_WORK);
                if (!this.mIsDoCoMo) {
                    parameterList.add(VCardConstants.PARAM_TYPE_PAGER);
                    break;
                } else {
                    parameterList.add(VCardConstants.PARAM_TYPE_VOICE);
                    break;
                }
            case 20:
                parameterList.add(VCardConstants.PARAM_TYPE_MSG);
                break;
        }
        if (isPrimary) {
            parameterList.add(VCardConstants.PARAM_TYPE_PREF);
        }
        if (parameterList.isEmpty()) {
            appendUncommonPhoneType(this.mBuilder, Integer.valueOf(type));
        } else {
            appendTypeParameters(parameterList);
        }
        this.mBuilder.append(VCARD_DATA_SEPARATOR);
        this.mBuilder.append(encodedValue);
        this.mBuilder.append(VCARD_END_OF_LINE);
    }

    private void appendUncommonPhoneType(StringBuilder builder, Integer type) {
        if (this.mIsDoCoMo) {
            builder.append(VCardConstants.PARAM_TYPE_VOICE);
            return;
        }
        String phoneType = VCardUtils.getPhoneTypeString(type);
        if (phoneType != null) {
            appendTypeParameter(phoneType);
            return;
        }
        Log.e(LOG_TAG, "Unknown or unsupported (by vCard) Phone type: " + type);
    }

    public void appendPhotoLine(String encodedValue, String photoType) {
        StringBuilder tmpBuilder = new StringBuilder();
        tmpBuilder.append(VCardConstants.PROPERTY_PHOTO);
        tmpBuilder.append(";");
        if (this.mIsV30OrV40) {
            tmpBuilder.append(VCARD_PARAM_ENCODING_BASE64_AS_B);
        } else {
            tmpBuilder.append(VCARD_PARAM_ENCODING_BASE64_V21);
        }
        tmpBuilder.append(";");
        appendTypeParameter(tmpBuilder, photoType);
        tmpBuilder.append(VCARD_DATA_SEPARATOR);
        tmpBuilder.append(encodedValue);
        String tmpStr = tmpBuilder.toString();
        StringBuilder tmpBuilder2 = new StringBuilder();
        int lineCount = 0;
        int length = tmpStr.length();
        int maxNumForFirstLine = 75 - VCARD_END_OF_LINE.length();
        int maxNumInGeneral = maxNumForFirstLine - VCARD_WS.length();
        int maxNum = maxNumForFirstLine;
        for (int i = 0; i < length; i++) {
            tmpBuilder2.append(tmpStr.charAt(i));
            lineCount++;
            if (lineCount > maxNum) {
                tmpBuilder2.append(VCARD_END_OF_LINE);
                tmpBuilder2.append(VCARD_WS);
                maxNum = maxNumInGeneral;
                lineCount = 0;
            }
        }
        this.mBuilder.append(tmpBuilder2.toString());
        this.mBuilder.append(VCARD_END_OF_LINE);
        this.mBuilder.append(VCARD_END_OF_LINE);
    }

    public VCardBuilder appendSipAddresses(List<ContentValues> contentValuesList) {
        boolean useXProperty;
        String propertyName;
        if (this.mIsV30OrV40) {
            useXProperty = false;
        } else if (!this.mUsesDefactProperty) {
            return this;
        } else {
            useXProperty = true;
        }
        if (contentValuesList != null) {
            for (ContentValues contentValues : contentValuesList) {
                String sipAddress = contentValues.getAsString("data1");
                if (!TextUtils.isEmpty(sipAddress)) {
                    if (useXProperty) {
                        if (sipAddress.startsWith("sip:")) {
                            if (sipAddress.length() != 4) {
                                sipAddress = sipAddress.substring(4);
                            }
                        }
                        appendLineWithCharsetAndQPDetection(VCardConstants.PROPERTY_X_SIP, sipAddress);
                    } else {
                        if (!sipAddress.startsWith("sip:")) {
                            sipAddress = "sip:" + sipAddress;
                        }
                        if (VCardConfig.isVersion40(this.mVCardType)) {
                            propertyName = VCardConstants.PROPERTY_TEL;
                        } else {
                            propertyName = VCardConstants.PROPERTY_IMPP;
                        }
                        appendLineWithCharsetAndQPDetection(propertyName, sipAddress);
                    }
                }
            }
        }
        return this;
    }

    public void appendAndroidSpecificProperty(String mimeType, ContentValues contentValues) {
        String encodedValue;
        if (sAllowedAndroidPropertySet.contains(mimeType)) {
            List<String> rawValueList = new ArrayList<>();
            for (int i = 1; i <= 15; i++) {
                String value = contentValues.getAsString("data" + i);
                if (value == null) {
                    value = "";
                }
                rawValueList.add(value);
            }
            boolean reallyUseQuotedPrintable = false;
            boolean needCharset = this.mShouldAppendCharsetParam && !VCardUtils.containsOnlyNonCrLfPrintableAscii(rawValueList);
            if (this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(rawValueList)) {
                reallyUseQuotedPrintable = true;
            }
            this.mBuilder.append(VCardConstants.PROPERTY_X_ANDROID_CUSTOM);
            if (needCharset) {
                this.mBuilder.append(";");
                this.mBuilder.append(this.mVCardCharsetParameter);
            }
            if (reallyUseQuotedPrintable) {
                this.mBuilder.append(";");
                this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
            }
            this.mBuilder.append(VCARD_DATA_SEPARATOR);
            this.mBuilder.append(mimeType);
            for (String rawValue : rawValueList) {
                if (reallyUseQuotedPrintable) {
                    encodedValue = encodeQuotedPrintable(rawValue);
                } else {
                    encodedValue = escapeCharacters(rawValue);
                }
                this.mBuilder.append(";");
                this.mBuilder.append(encodedValue);
            }
            this.mBuilder.append(VCARD_END_OF_LINE);
        }
    }

    public void appendLineWithCharsetAndQPDetection(String propertyName, String rawValue) {
        appendLineWithCharsetAndQPDetection(propertyName, (List<String>) null, rawValue);
    }

    public void appendLineWithCharsetAndQPDetection(String propertyName, List<String> rawValueList) {
        appendLineWithCharsetAndQPDetection(propertyName, (List<String>) null, rawValueList);
    }

    public void appendLineWithCharsetAndQPDetection(String propertyName, List<String> parameterList, String rawValue) {
        appendLine(propertyName, parameterList, rawValue, !VCardUtils.containsOnlyPrintableAscii(rawValue), this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(new String[]{rawValue}));
    }

    public void appendLineWithCharsetAndQPDetection(String propertyName, List<String> parameterList, List<String> rawValueList) {
        appendLine(propertyName, parameterList, rawValueList, this.mShouldAppendCharsetParam && !VCardUtils.containsOnlyNonCrLfPrintableAscii(rawValueList), this.mShouldUseQuotedPrintable && !VCardUtils.containsOnlyNonCrLfPrintableAscii(rawValueList));
    }

    public void appendLine(String propertyName, String rawValue) {
        appendLine(propertyName, rawValue, false, false);
    }

    public void appendLine(String propertyName, List<String> rawValueList) {
        appendLine(propertyName, rawValueList, false, false);
    }

    public void appendLine(String propertyName, String rawValue, boolean needCharset, boolean reallyUseQuotedPrintable) {
        appendLine(propertyName, (List<String>) null, rawValue, needCharset, reallyUseQuotedPrintable);
    }

    public void appendLine(String propertyName, List<String> parameterList, String rawValue) {
        appendLine(propertyName, parameterList, rawValue, false, false);
    }

    public void appendLine(String propertyName, List<String> parameterList, String rawValue, boolean needCharset, boolean reallyUseQuotedPrintable) {
        String encodedValue;
        this.mBuilder.append(propertyName);
        if (parameterList != null && parameterList.size() > 0) {
            this.mBuilder.append(";");
            appendTypeParameters(parameterList);
        }
        if (needCharset) {
            this.mBuilder.append(";");
            this.mBuilder.append(this.mVCardCharsetParameter);
        }
        if (reallyUseQuotedPrintable) {
            this.mBuilder.append(";");
            this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
            encodedValue = encodeQuotedPrintable(rawValue);
        } else {
            encodedValue = escapeCharacters(rawValue);
        }
        this.mBuilder.append(VCARD_DATA_SEPARATOR);
        this.mBuilder.append(encodedValue);
        this.mBuilder.append(VCARD_END_OF_LINE);
    }

    public void appendLine(String propertyName, List<String> rawValueList, boolean needCharset, boolean needQuotedPrintable) {
        appendLine(propertyName, (List<String>) null, rawValueList, needCharset, needQuotedPrintable);
    }

    public void appendLine(String propertyName, List<String> parameterList, List<String> rawValueList, boolean needCharset, boolean needQuotedPrintable) {
        String encodedValue;
        this.mBuilder.append(propertyName);
        if (parameterList != null && parameterList.size() > 0) {
            this.mBuilder.append(";");
            appendTypeParameters(parameterList);
        }
        if (needCharset) {
            this.mBuilder.append(";");
            this.mBuilder.append(this.mVCardCharsetParameter);
        }
        if (needQuotedPrintable) {
            this.mBuilder.append(";");
            this.mBuilder.append(VCARD_PARAM_ENCODING_QP);
        }
        this.mBuilder.append(VCARD_DATA_SEPARATOR);
        boolean first = true;
        for (String rawValue : rawValueList) {
            if (needQuotedPrintable) {
                encodedValue = encodeQuotedPrintable(rawValue);
            } else {
                encodedValue = escapeCharacters(rawValue);
            }
            if (first) {
                first = false;
            } else {
                this.mBuilder.append(";");
            }
            this.mBuilder.append(encodedValue);
        }
        this.mBuilder.append(VCARD_END_OF_LINE);
    }

    private void appendTypeParameters(List<String> types) {
        String encoded;
        boolean first = true;
        for (String typeValue : types) {
            if (VCardConfig.isVersion30(this.mVCardType) || VCardConfig.isVersion40(this.mVCardType)) {
                if (VCardConfig.isVersion40(this.mVCardType)) {
                    encoded = VCardUtils.toStringAsV40ParamValue(typeValue);
                } else {
                    encoded = VCardUtils.toStringAsV30ParamValue(typeValue);
                }
                if (!TextUtils.isEmpty(encoded)) {
                    if (first) {
                        first = false;
                    } else {
                        this.mBuilder.append(";");
                    }
                    appendTypeParameter(encoded);
                }
            } else if (VCardUtils.isV21Word(typeValue)) {
                if (first) {
                    first = false;
                } else {
                    this.mBuilder.append(";");
                }
                appendTypeParameter(typeValue);
            }
        }
    }

    private void appendTypeParameter(String type) {
        appendTypeParameter(this.mBuilder, type);
    }

    private void appendTypeParameter(StringBuilder builder, String type) {
        if (VCardConfig.isVersion40(this.mVCardType) || ((VCardConfig.isVersion30(this.mVCardType) || this.mAppendTypeParamName) && !this.mIsDoCoMo)) {
            builder.append(VCardConstants.PARAM_TYPE);
            builder.append(VCARD_PARAM_EQUAL);
        }
        builder.append(type);
    }

    private boolean shouldAppendCharsetParam(String... propertyValueList) {
        if (!this.mShouldAppendCharsetParam) {
            return false;
        }
        int length = propertyValueList.length;
        for (int i = 0; i < length; i++) {
            if (!VCardUtils.containsOnlyPrintableAscii(propertyValueList[i])) {
                return true;
            }
        }
        return false;
    }

    private String encodeQuotedPrintable(String str) {
        byte[] strArray;
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        int index = 0;
        int lineCount = 0;
        try {
            strArray = str.getBytes(this.mCharset);
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "Charset " + this.mCharset + " cannot be used. Try default charset");
            strArray = str.getBytes();
        }
        while (index < strArray.length) {
            builder.append(String.format("=%02X", Byte.valueOf(strArray[index])));
            index++;
            lineCount += 3;
            if (lineCount >= 67) {
                builder.append("=\r\n");
                lineCount = 0;
            }
        }
        return builder.toString();
    }

    private String escapeCharacters(String unescaped) {
        if (TextUtils.isEmpty(unescaped)) {
            return "";
        }
        StringBuilder tmpBuilder = new StringBuilder();
        int length = unescaped.length();
        for (int i = 0; i < length; i++) {
            char ch = unescaped.charAt(i);
            if (ch != '\n') {
                if (ch != '\r') {
                    if (ch != ',') {
                        if (ch != '>') {
                            if (ch != '\\') {
                                if (ch == ';') {
                                    tmpBuilder.append('\\');
                                    tmpBuilder.append(';');
                                } else if (ch != '<') {
                                    tmpBuilder.append(ch);
                                }
                            } else if (this.mIsV30OrV40) {
                                tmpBuilder.append("\\\\");
                            }
                        }
                        if (this.mIsDoCoMo) {
                            tmpBuilder.append('\\');
                            tmpBuilder.append(ch);
                        } else {
                            tmpBuilder.append(ch);
                        }
                    } else if (this.mIsV30OrV40) {
                        tmpBuilder.append("\\,");
                    } else {
                        tmpBuilder.append(ch);
                    }
                } else if (i + 1 < length && unescaped.charAt(i) == '\n') {
                }
            }
            tmpBuilder.append("\\n");
        }
        return tmpBuilder.toString();
    }

    public String toString() {
        if (!this.mEndAppended) {
            if (this.mIsDoCoMo) {
                appendLine(VCardConstants.PROPERTY_X_CLASS, VCARD_DATA_PUBLIC);
                appendLine(VCardConstants.PROPERTY_X_REDUCTION, "");
                appendLine(VCardConstants.PROPERTY_X_NO, "");
                appendLine(VCardConstants.PROPERTY_X_DCM_HMN_MODE, "");
            }
            appendLine(VCardConstants.PROPERTY_END, VCARD_DATA_VCARD);
            this.mEndAppended = true;
        }
        return this.mBuilder.toString();
    }
}
