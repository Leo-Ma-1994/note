package net.sourceforge.pinyin4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/* access modifiers changed from: package-private */
public class ChineseToPinyinResource {
    private Properties unicodeToHanyuPinyinTable;

    private void setUnicodeToHanyuPinyinTable(Properties unicodeToHanyuPinyinTable2) {
        this.unicodeToHanyuPinyinTable = unicodeToHanyuPinyinTable2;
    }

    private Properties getUnicodeToHanyuPinyinTable() {
        return this.unicodeToHanyuPinyinTable;
    }

    private ChineseToPinyinResource() {
        this.unicodeToHanyuPinyinTable = null;
        initializeResource();
    }

    private void initializeResource() {
        try {
            setUnicodeToHanyuPinyinTable(new Properties());
            getUnicodeToHanyuPinyinTable().load(ResourceHelper.getResourceInputStream("/pinyindb/unicode_to_hanyu_pinyin.txt"));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex2) {
            ex2.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public String[] getHanyuPinyinStringArray(char ch) {
        String pinyinRecord = getHanyuPinyinRecordFromChar(ch);
        if (pinyinRecord == null) {
            return null;
        }
        int indexOfLeftBracket = pinyinRecord.indexOf("(");
        return pinyinRecord.substring("(".length() + indexOfLeftBracket, pinyinRecord.lastIndexOf(")")).split(",");
    }

    private boolean isValidRecord(String record) {
        if (record == null || record.equals("(none0)") || !record.startsWith("(") || !record.endsWith(")")) {
            return false;
        }
        return true;
    }

    private String getHanyuPinyinRecordFromChar(char ch) {
        String foundRecord = getUnicodeToHanyuPinyinTable().getProperty(Integer.toHexString(ch).toUpperCase());
        if (isValidRecord(foundRecord)) {
            return foundRecord;
        }
        return null;
    }

    static ChineseToPinyinResource getInstance() {
        return ChineseToPinyinResourceHolder.theInstance;
    }

    /* access modifiers changed from: private */
    public static class ChineseToPinyinResourceHolder {
        static final ChineseToPinyinResource theInstance = new ChineseToPinyinResource();

        private ChineseToPinyinResourceHolder() {
        }
    }

    class Field {
        static final String COMMA = ",";
        static final String LEFT_BRACKET = "(";
        static final String RIGHT_BRACKET = ")";

        Field() {
        }
    }
}
