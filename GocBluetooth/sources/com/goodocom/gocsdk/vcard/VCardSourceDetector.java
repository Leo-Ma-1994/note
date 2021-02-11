package com.goodocom.gocsdk.vcard;

import android.text.TextUtils;
import android.util.Log;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VCardSourceDetector implements VCardInterpreter {
    private static Set<String> APPLE_SIGNS = new HashSet(Arrays.asList(VCardConstants.PROPERTY_X_PHONETIC_FIRST_NAME, VCardConstants.PROPERTY_X_PHONETIC_MIDDLE_NAME, VCardConstants.PROPERTY_X_PHONETIC_LAST_NAME, "X-ABADR", "X-ABUID"));
    private static Set<String> FOMA_SIGNS = new HashSet(Arrays.asList("X-SD-VERN", "X-SD-FORMAT_VER", "X-SD-CATEGORIES", "X-SD-CLASS", "X-SD-DCREATED", "X-SD-DESCRIPTION"));
    private static Set<String> JAPANESE_MOBILE_PHONE_SIGNS = new HashSet(Arrays.asList("X-GNO", "X-GN", VCardConstants.PROPERTY_X_REDUCTION));
    private static final String LOG_TAG = "vCard";
    private static final int PARSE_TYPE_APPLE = 1;
    private static final int PARSE_TYPE_DOCOMO_FOMA = 3;
    private static final int PARSE_TYPE_MOBILE_PHONE_JP = 2;
    public static final int PARSE_TYPE_UNKNOWN = 0;
    private static final int PARSE_TYPE_WINDOWS_MOBILE_V65_JP = 4;
    private static String TYPE_FOMA_CHARSET_SIGN = "X-SD-CHAR_CODE";
    private static Set<String> WINDOWS_MOBILE_PHONE_SIGNS = new HashSet(Arrays.asList("X-MICROSOFT-ASST_TEL", "X-MICROSOFT-ASSISTANT", "X-MICROSOFT-OFFICELOC"));
    private int mParseType = 0;
    private String mSpecifiedCharset;
    private int mVersion = -1;

    @Override // com.goodocom.gocsdk.vcard.VCardInterpreter
    public void onVCardStarted() {
    }

    @Override // com.goodocom.gocsdk.vcard.VCardInterpreter
    public void onVCardEnded() {
    }

    @Override // com.goodocom.gocsdk.vcard.VCardInterpreter
    public void onEntryStarted() {
    }

    @Override // com.goodocom.gocsdk.vcard.VCardInterpreter
    public void onEntryEnded() {
    }

    @Override // com.goodocom.gocsdk.vcard.VCardInterpreter
    public void onPropertyCreated(VCardProperty property) {
        String propertyName = property.getName();
        List<String> valueList = property.getValueList();
        if (propertyName.equalsIgnoreCase(VCardConstants.PROPERTY_VERSION) && valueList.size() > 0) {
            String versionString = valueList.get(0);
            if (versionString.equals(VCardConstants.VERSION_V21)) {
                this.mVersion = 0;
            } else if (versionString.equals(VCardConstants.VERSION_V30)) {
                this.mVersion = 1;
            } else if (versionString.equals(VCardConstants.VERSION_V40)) {
                this.mVersion = 2;
            } else {
                Log.w(LOG_TAG, "Invalid version string: " + versionString);
            }
        } else if (propertyName.equalsIgnoreCase(TYPE_FOMA_CHARSET_SIGN)) {
            this.mParseType = 3;
            if (valueList.size() > 0) {
                this.mSpecifiedCharset = valueList.get(0);
            }
        }
        if (this.mParseType == 0) {
            if (WINDOWS_MOBILE_PHONE_SIGNS.contains(propertyName)) {
                this.mParseType = 4;
            } else if (FOMA_SIGNS.contains(propertyName)) {
                this.mParseType = 3;
            } else if (JAPANESE_MOBILE_PHONE_SIGNS.contains(propertyName)) {
                this.mParseType = 2;
            } else if (APPLE_SIGNS.contains(propertyName)) {
                this.mParseType = 1;
            }
        }
    }

    public int getEstimatedType() {
        int i = this.mParseType;
        if (i == 2) {
            return VCardConfig.VCARD_TYPE_V21_JAPANESE_MOBILE;
        }
        if (i == 3) {
            return VCardConfig.VCARD_TYPE_DOCOMO;
        }
        int i2 = this.mVersion;
        if (i2 == 0) {
            return VCardConfig.VCARD_TYPE_V21_GENERIC;
        }
        if (i2 == 1) {
            return VCardConfig.VCARD_TYPE_V30_GENERIC;
        }
        if (i2 == 2) {
            return VCardConfig.VCARD_TYPE_V40_GENERIC;
        }
        return 0;
    }

    public String getEstimatedCharset() {
        if (TextUtils.isEmpty(this.mSpecifiedCharset)) {
            return this.mSpecifiedCharset;
        }
        int i = this.mParseType;
        if (i == 1) {
            return "UTF-8";
        }
        if (i == 2 || i == 3 || i == 4) {
            return "SHIFT_JIS";
        }
        return null;
    }
}
