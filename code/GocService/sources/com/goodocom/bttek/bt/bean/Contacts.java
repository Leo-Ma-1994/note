package com.goodocom.bttek.bt.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class Contacts implements Parcelable {
    public static final Parcelable.Creator<Contacts> CREATOR = new Parcelable.Creator<Contacts>() {
        /* class com.goodocom.bttek.bt.bean.Contacts.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public Contacts createFromParcel(Parcel source) {
            return new Contacts(source.readInt(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readString(), source.readInt(), source.readString());
        }

        @Override // android.os.Parcelable.Creator
        public Contacts[] newArray(int size) {
            return new Contacts[size];
        }
    };
    private int collection;
    private String firstWord;
    private int id;
    private String imgPath;
    private String name;
    private String numberJSON;
    private String pinyin;
    private List<String> pinyinList;
    private String szm;
    private String typeJSON;

    public Contacts() {
    }

    public Contacts(String name2, String numberJSON2) {
        this.name = name2;
        this.numberJSON = numberJSON2;
    }

    public Contacts(String name2, String numberJSON2, String typeJSON2, String pinyin2, String szm2, String imgPath2) {
        this.name = name2;
        this.numberJSON = numberJSON2;
        this.typeJSON = typeJSON2;
        this.pinyin = pinyin2;
        this.szm = szm2;
        this.imgPath = imgPath2;
    }

    public Contacts(String name2, String numberJSON2, String typeJSON2, String pinyin2, String szm2) {
        this.name = name2;
        this.numberJSON = numberJSON2;
        this.typeJSON = typeJSON2;
        this.pinyin = pinyin2;
        this.szm = szm2;
    }

    public Contacts(int id2, String name2, String numberJSON2, String typeJSON2, String pinyin2, String szm2, int collection2) {
        this.id = id2;
        this.name = name2;
        this.numberJSON = numberJSON2;
        this.typeJSON = typeJSON2;
        this.pinyin = pinyin2;
        this.szm = szm2;
        this.collection = collection2;
    }

    public Contacts(int id2, String name2, String numberJSON2, String typeJSON2, String pinyin2, String szm2, String imgPath2, int collection2, String firstWord2) {
        this.id = id2;
        this.name = name2;
        this.numberJSON = numberJSON2;
        this.typeJSON = typeJSON2;
        this.pinyin = pinyin2;
        this.szm = szm2;
        this.imgPath = imgPath2;
        this.collection = collection2;
        this.firstWord = firstWord2;
    }

    public int getCollection() {
        return this.collection;
    }

    public void setCollection(int collection2) {
        this.collection = collection2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getNumberJSON() {
        return this.numberJSON;
    }

    public void setNumberJSON(String numberJSON2) {
        this.numberJSON = numberJSON2;
    }

    public String getTypeJSON() {
        return this.typeJSON;
    }

    public void setTypeJSON(String typeJSON2) {
        this.typeJSON = typeJSON2;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin2) {
        this.pinyin = pinyin2;
    }

    public List<String> getPinyinList() {
        return this.pinyinList;
    }

    public void setPinyinList(List<String> pinyinList2) {
        this.pinyinList = pinyinList2;
    }

    public String getSzm() {
        return this.szm;
    }

    public void setSzm(String szm2) {
        this.szm = szm2;
    }

    public String getImgPath() {
        return this.imgPath;
    }

    public void setImgPath(String imgPath2) {
        this.imgPath = imgPath2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.numberJSON);
        dest.writeString(this.typeJSON);
        dest.writeString(this.pinyin);
        dest.writeString(this.szm);
        dest.writeString(this.imgPath);
        dest.writeInt(this.collection);
        dest.writeString(this.firstWord);
    }

    public void setFirstWord(String firstWord2) {
        this.firstWord = firstWord2;
    }

    public String getFirstWord() {
        return this.firstWord;
    }
}
