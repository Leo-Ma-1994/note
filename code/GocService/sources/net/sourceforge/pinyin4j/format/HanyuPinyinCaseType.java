package net.sourceforge.pinyin4j.format;

public class HanyuPinyinCaseType {
    public static final HanyuPinyinCaseType LOWERCASE = new HanyuPinyinCaseType("LOWERCASE");
    public static final HanyuPinyinCaseType UPPERCASE = new HanyuPinyinCaseType("UPPERCASE");
    protected String name;

    public String getName() {
        return this.name;
    }

    /* access modifiers changed from: protected */
    public void setName(String name2) {
        this.name = name2;
    }

    protected HanyuPinyinCaseType(String name2) {
        setName(name2);
    }
}
