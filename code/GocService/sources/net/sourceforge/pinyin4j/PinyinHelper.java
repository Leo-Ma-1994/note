package net.sourceforge.pinyin4j;

import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinHelper {
    public static String[] toHanyuPinyinStringArray(char ch) {
        return getUnformattedHanyuPinyinStringArray(ch);
    }

    public static String[] toHanyuPinyinStringArray(char ch, HanyuPinyinOutputFormat outputFormat) throws BadHanyuPinyinOutputFormatCombination {
        return getFormattedHanyuPinyinStringArray(ch, outputFormat);
    }

    private static String[] getFormattedHanyuPinyinStringArray(char ch, HanyuPinyinOutputFormat outputFormat) throws BadHanyuPinyinOutputFormatCombination {
        String[] pinyinStrArray = getUnformattedHanyuPinyinStringArray(ch);
        if (pinyinStrArray == null) {
            return null;
        }
        for (int i = 0; i < pinyinStrArray.length; i++) {
            pinyinStrArray[i] = PinyinFormatter.formatHanyuPinyin(pinyinStrArray[i], outputFormat);
        }
        return pinyinStrArray;
    }

    private static String[] getUnformattedHanyuPinyinStringArray(char ch) {
        return ChineseToPinyinResource.getInstance().getHanyuPinyinStringArray(ch);
    }

    public static String toHanyuPinyinString(String str, HanyuPinyinOutputFormat outputFormat, String seperater) throws BadHanyuPinyinOutputFormatCombination {
        StringBuffer resultPinyinStrBuf = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            String mainPinyinStrOfChar = getFirstHanyuPinyinString(str.charAt(i), outputFormat);
            if (mainPinyinStrOfChar != null) {
                resultPinyinStrBuf.append(mainPinyinStrOfChar);
                if (i != str.length() - 1) {
                    resultPinyinStrBuf.append(seperater);
                }
            } else {
                resultPinyinStrBuf.append(str.charAt(i));
            }
        }
        return resultPinyinStrBuf.toString();
    }

    private static String getFirstHanyuPinyinString(char ch, HanyuPinyinOutputFormat outputFormat) throws BadHanyuPinyinOutputFormatCombination {
        String[] pinyinStrArray = getFormattedHanyuPinyinStringArray(ch, outputFormat);
        if (pinyinStrArray == null || pinyinStrArray.length <= 0) {
            return null;
        }
        return pinyinStrArray[0];
    }

    private PinyinHelper() {
    }
}
