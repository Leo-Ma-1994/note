package com.goodocom.utils;

import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class HanyuPinyinHelper {
    public static String toHanyuPinyin(String ChineseLanguage) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = BuildConfig.FLAVOR;
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        if (cl_chars == null) {
            try {
                Log.e("pinyin", "cl_chars is null");
                return null;
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                Log.e("pinyin", "字符不能转成汉语拼音");
            }
        } else {
            for (int i = 0; i < cl_chars.length; i++) {
                if (String.valueOf(cl_chars[i]).matches("[一-龥]+")) {
                    String[] pinyinArry = PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat);
                    hanyupinyin = (pinyinArry == null || pinyinArry.length <= 0 || pinyinArry[0] == null) ? hanyupinyin + "?" : hanyupinyin + pinyinArry[0];
                } else {
                    hanyupinyin = hanyupinyin + cl_chars[i];
                }
            }
            return hanyupinyin;
        }
    }

    public static String getFirstLettersUp(String ChineseLanguage) {
        return getFirstLetters(ChineseLanguage, HanyuPinyinCaseType.UPPERCASE);
    }

    public static String getFirstLettersLo(String ChineseLanguage) {
        return getFirstLetters(ChineseLanguage, HanyuPinyinCaseType.LOWERCASE);
    }

    public static String getFirstLetters(String ChineseLanguage, HanyuPinyinCaseType caseType) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = BuildConfig.FLAVOR;
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(caseType);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < cl_chars.length; i++) {
            try {
                String str = String.valueOf(cl_chars[i]);
                if (str.matches("[一-龥]+")) {
                    String[] toHanyuPinyinStringArrays = PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat);
                    if (toHanyuPinyinStringArrays != null) {
                        if (toHanyuPinyinStringArrays.length != 0) {
                            String toHanyuPinyinStringArray = toHanyuPinyinStringArrays[0].substring(0, 1);
                            if (toHanyuPinyinStringArray != null) {
                                hanyupinyin = hanyupinyin + toHanyuPinyinStringArray;
                            }
                        }
                    }
                } else if (str.matches("[0-9]+")) {
                    hanyupinyin = hanyupinyin + cl_chars[i];
                } else if (str.matches("[a-zA-Z]+")) {
                    hanyupinyin = hanyupinyin + cl_chars[i];
                } else {
                    hanyupinyin = hanyupinyin + cl_chars[i];
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                Log.e("pinyin", "字符不能转成汉语拼音");
            }
        }
        return hanyupinyin;
    }

    public static String getPinyinString(String ChineseLanguage) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        String hanyupinyin = BuildConfig.FLAVOR;
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < cl_chars.length; i++) {
            try {
                String str = String.valueOf(cl_chars[i]);
                if (str.matches("[一-龥]+")) {
                    hanyupinyin = hanyupinyin + PinyinHelper.toHanyuPinyinStringArray(cl_chars[i], defaultFormat)[0];
                } else if (str.matches("[0-9]+")) {
                    hanyupinyin = hanyupinyin + cl_chars[i];
                } else if (str.matches("[a-zA-Z]+")) {
                    hanyupinyin = hanyupinyin + cl_chars[i];
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                Log.e("pinyin", "字符不能转成汉语拼音");
            }
        }
        return hanyupinyin;
    }

    public static String getFirstLetter(String ChineseLanguage) {
        char[] cl_chars = ChineseLanguage.trim().toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        try {
            String str = String.valueOf(cl_chars[0]);
            if (str.matches("[一-龥]+")) {
                return PinyinHelper.toHanyuPinyinStringArray(cl_chars[0], defaultFormat)[0].substring(0, 1);
            }
            if (str.matches("[0-9]+")) {
                return BuildConfig.FLAVOR + cl_chars[0];
            } else if (!str.matches("[a-zA-Z]+")) {
                return BuildConfig.FLAVOR;
            } else {
                return BuildConfig.FLAVOR + cl_chars[0];
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            Log.e("pinyin  ", "字符不能转成汉语拼音");
            return BuildConfig.FLAVOR;
        }
    }
}
