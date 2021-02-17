package com.goodocom.gocsdk.vcard;

import android.util.Log;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VCardConfig {
    public static final String DEFAULT_EXPORT_CHARSET = "UTF-8";
    public static final String DEFAULT_IMPORT_CHARSET = "UTF-8";
    public static final String DEFAULT_INTERMEDIATE_CHARSET = "ISO-8859-1";
    public static final int FLAG_APPEND_TYPE_PARAM = 67108864;
    public static final int FLAG_CONVERT_PHONETIC_NAME_STRINGS = 134217728;
    private static final int FLAG_DOCOMO = 536870912;
    public static final int FLAG_REFRAIN_IMAGE_EXPORT = 8388608;
    public static final int FLAG_REFRAIN_PHONE_NUMBER_FORMATTING = 33554432;
    public static final int FLAG_REFRAIN_QP_TO_NAME_PROPERTIES = 268435456;
    private static final int FLAG_USE_ANDROID_PROPERTY = Integer.MIN_VALUE;
    private static final int FLAG_USE_DEFACT_PROPERTY = 1073741824;
    static final int LOG_LEVEL = 0;
    static final int LOG_LEVEL_NONE = 0;
    static final int LOG_LEVEL_PERFORMANCE_MEASUREMENT = 1;
    static final int LOG_LEVEL_SHOW_WARNING = 2;
    static final int LOG_LEVEL_VERBOSE = 3;
    private static final String LOG_TAG = "vCard";
    public static final int NAME_ORDER_DEFAULT = 0;
    public static final int NAME_ORDER_EUROPE = 4;
    public static final int NAME_ORDER_JAPANESE = 8;
    private static final int NAME_ORDER_MASK = 12;
    public static int VCARD_TYPE_DEFAULT = VCARD_TYPE_V21_GENERIC;
    public static final int VCARD_TYPE_DOCOMO = 939524104;
    static final String VCARD_TYPE_DOCOMO_STR = "docomo";
    public static final int VCARD_TYPE_UNKNOWN = 0;
    public static final int VCARD_TYPE_V21_EUROPE = -1073741820;
    static final String VCARD_TYPE_V21_EUROPE_STR = "v21_europe";
    public static final int VCARD_TYPE_V21_GENERIC = -1073741824;
    static String VCARD_TYPE_V21_GENERIC_STR = "v21_generic";
    public static final int VCARD_TYPE_V21_JAPANESE = -1073741816;
    public static final int VCARD_TYPE_V21_JAPANESE_MOBILE = 402653192;
    static final String VCARD_TYPE_V21_JAPANESE_MOBILE_STR = "v21_japanese_mobile";
    static final String VCARD_TYPE_V21_JAPANESE_STR = "v21_japanese_utf8";
    public static final int VCARD_TYPE_V30_EUROPE = -1073741819;
    static final String VCARD_TYPE_V30_EUROPE_STR = "v30_europe";
    public static final int VCARD_TYPE_V30_GENERIC = -1073741823;
    static final String VCARD_TYPE_V30_GENERIC_STR = "v30_generic";
    public static final int VCARD_TYPE_V30_JAPANESE = -1073741815;
    static final String VCARD_TYPE_V30_JAPANESE_STR = "v30_japanese_utf8";
    public static final int VCARD_TYPE_V40_GENERIC = -1073741822;
    static final String VCARD_TYPE_V40_GENERIC_STR = "v40_generic";
    public static final int VERSION_21 = 0;
    public static final int VERSION_30 = 1;
    public static final int VERSION_40 = 2;
    public static final int VERSION_MASK = 3;
    private static final Set<Integer> sJapaneseMobileTypeSet = new HashSet();
    private static final Map<String, Integer> sVCardTypeMap = new HashMap();

    static {
        sVCardTypeMap.put(VCARD_TYPE_V21_GENERIC_STR, Integer.valueOf((int) VCARD_TYPE_V21_GENERIC));
        sVCardTypeMap.put(VCARD_TYPE_V30_GENERIC_STR, Integer.valueOf((int) VCARD_TYPE_V30_GENERIC));
        sVCardTypeMap.put(VCARD_TYPE_V21_EUROPE_STR, Integer.valueOf((int) VCARD_TYPE_V21_EUROPE));
        sVCardTypeMap.put(VCARD_TYPE_V30_EUROPE_STR, Integer.valueOf((int) VCARD_TYPE_V30_EUROPE));
        Map<String, Integer> map = sVCardTypeMap;
        Integer valueOf = Integer.valueOf((int) VCARD_TYPE_V21_JAPANESE);
        map.put(VCARD_TYPE_V21_JAPANESE_STR, valueOf);
        Map<String, Integer> map2 = sVCardTypeMap;
        Integer valueOf2 = Integer.valueOf((int) VCARD_TYPE_V30_JAPANESE);
        map2.put(VCARD_TYPE_V30_JAPANESE_STR, valueOf2);
        Map<String, Integer> map3 = sVCardTypeMap;
        Integer valueOf3 = Integer.valueOf((int) VCARD_TYPE_V21_JAPANESE_MOBILE);
        map3.put(VCARD_TYPE_V21_JAPANESE_MOBILE_STR, valueOf3);
        Map<String, Integer> map4 = sVCardTypeMap;
        Integer valueOf4 = Integer.valueOf((int) VCARD_TYPE_DOCOMO);
        map4.put(VCARD_TYPE_DOCOMO_STR, valueOf4);
        sJapaneseMobileTypeSet.add(valueOf);
        sJapaneseMobileTypeSet.add(valueOf2);
        sJapaneseMobileTypeSet.add(valueOf3);
        sJapaneseMobileTypeSet.add(valueOf4);
    }

    public static int getVCardTypeFromString(String vcardTypeString) {
        String loweredKey = vcardTypeString.toLowerCase();
        if (sVCardTypeMap.containsKey(loweredKey)) {
            return sVCardTypeMap.get(loweredKey).intValue();
        }
        if ("default".equalsIgnoreCase(vcardTypeString)) {
            return VCARD_TYPE_DEFAULT;
        }
        Log.e(LOG_TAG, "Unknown vCard type String: \"" + vcardTypeString + "\"");
        return VCARD_TYPE_DEFAULT;
    }

    public static boolean isVersion21(int vcardType) {
        return (vcardType & 3) == 0;
    }

    public static boolean isVersion30(int vcardType) {
        return (vcardType & 3) == 1;
    }

    public static boolean isVersion40(int vcardType) {
        return (vcardType & 3) == 2;
    }

    public static boolean shouldUseQuotedPrintable(int vcardType) {
        return !isVersion30(vcardType);
    }

    public static int getNameOrderType(int vcardType) {
        return vcardType & 12;
    }

    public static boolean usesAndroidSpecificProperty(int vcardType) {
        return (Integer.MIN_VALUE & vcardType) != 0;
    }

    public static boolean usesDefactProperty(int vcardType) {
        return (1073741824 & vcardType) != 0;
    }

    public static boolean showPerformanceLog() {
        return false;
    }

    public static boolean shouldRefrainQPToNameProperties(int vcardType) {
        return !shouldUseQuotedPrintable(vcardType) || (268435456 & vcardType) != 0;
    }

    public static boolean appendTypeParamName(int vcardType) {
        return isVersion30(vcardType) || (67108864 & vcardType) != 0;
    }

    public static boolean isJapaneseDevice(int vcardType) {
        return sJapaneseMobileTypeSet.contains(Integer.valueOf(vcardType));
    }

    static boolean refrainPhoneNumberFormatting(int vcardType) {
        return (33554432 & vcardType) != 0;
    }

    public static boolean needsToConvertPhoneticString(int vcardType) {
        return (134217728 & vcardType) != 0;
    }

    public static boolean onlyOneNoteFieldIsAvailable(int vcardType) {
        return vcardType == 939524104;
    }

    public static boolean isDoCoMo(int vcardType) {
        return (FLAG_DOCOMO & vcardType) != 0;
    }

    private VCardConfig() {
    }
}
