package com.goodocom.bttek.bt.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.goodocom.bttek.BuildConfig;

public class Collection implements Parcelable {
    public static final Parcelable.Creator<Collection> CREATOR = new Parcelable.Creator<Collection>() {
        /* class com.goodocom.bttek.bt.bean.Collection.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public Collection createFromParcel(Parcel source) {
            return new Collection(source.readInt(), source.readString(), source.readString(), source.readString(), source.readString(), source.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public Collection[] newArray(int size) {
            return new Collection[size];
        }
    };
    private int cid;
    private int id;
    private String imgPath;
    private String name;
    private String numberJSON;
    private String typeJSON;

    public Collection() {
    }

    public Collection(String name2, String numberJSON2) {
        this.name = name2;
        this.numberJSON = numberJSON2;
    }

    public Collection(String name2, String numberJSON2, String typeJSON2, String imgPath2, int cid2) {
        this.name = name2;
        this.numberJSON = numberJSON2;
        this.typeJSON = typeJSON2;
        this.imgPath = imgPath2;
        this.cid = cid2;
    }

    public Collection(int id2, String name2, String numberJSON2, String typeJSON2, String imgPath2, int cid2) {
        this.id = id2;
        this.name = name2;
        this.numberJSON = numberJSON2;
        this.typeJSON = typeJSON2;
        this.imgPath = imgPath2;
        this.cid = cid2;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid2) {
        this.cid = cid2;
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
        String str = this.name;
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        dest.writeString(str);
        String str2 = this.numberJSON;
        if (str2 == null) {
            str2 = BuildConfig.FLAVOR;
        }
        dest.writeString(str2);
        String str3 = this.typeJSON;
        if (str3 == null) {
            str3 = BuildConfig.FLAVOR;
        }
        dest.writeString(str3);
        String str4 = this.imgPath;
        if (str4 == null) {
            str4 = BuildConfig.FLAVOR;
        }
        dest.writeString(str4);
        dest.writeInt(this.cid);
    }
}
