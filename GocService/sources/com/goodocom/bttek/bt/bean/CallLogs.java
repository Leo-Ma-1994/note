package com.goodocom.bttek.bt.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CallLogs implements Parcelable {
    public static final Parcelable.Creator<CallLogs> CREATOR = new Parcelable.Creator<CallLogs>() {
        /* class com.goodocom.bttek.bt.bean.CallLogs.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public CallLogs createFromParcel(Parcel source) {
            return new CallLogs(source.readInt(), source.readInt(), source.readString(), source.readString(), source.readString());
        }

        @Override // android.os.Parcelable.Creator
        public CallLogs[] newArray(int size) {
            return new CallLogs[size];
        }
    };
    private int id;
    private String name;
    private String number;
    private String time;
    private int type;

    public CallLogs() {
    }

    public CallLogs(int type2, String name2, String number2, String time2) {
        this.type = type2;
        this.name = name2;
        this.number = number2;
        this.time = time2;
    }

    public CallLogs(int id2, int type2, String name2, String number2, String time2) {
        this.type = type2;
        this.name = name2;
        this.id = id2;
        this.number = number2;
        this.time = time2;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id2) {
        this.id = id2;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type2) {
        this.type = type2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number2) {
        this.number = number2;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time2) {
        this.time = time2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.type);
        dest.writeString(this.name);
        dest.writeString(this.number);
        dest.writeString(this.time);
    }
}
