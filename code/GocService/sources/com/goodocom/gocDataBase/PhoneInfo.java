package com.goodocom.gocDataBase;

import java.io.Serializable;

public class PhoneInfo implements Serializable {
    private String calledHistoryDate;
    private String calledHistoryTime;
    private String phoneNumber;
    private String phoneType;
    private String phoneTypeName;

    public String getPhoneType() {
        return this.phoneType;
    }

    public void setPhoneType(String phoneType2) {
        this.phoneType = phoneType2;
    }

    public String getPhoneTypeName() {
        return this.phoneTypeName;
    }

    public void setPhoneTypeName(String phoneTypeName2) {
        this.phoneTypeName = phoneTypeName2;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber2) {
        this.phoneNumber = phoneNumber2;
    }

    public String getCalledHistoryDate() {
        return this.calledHistoryDate;
    }

    public void setCalledHistoryDate(String calledHistoryDate2) {
        this.calledHistoryDate = calledHistoryDate2;
    }

    public String getCalledHistoryTime() {
        return this.calledHistoryTime;
    }

    public void setCalledHistoryTime(String calledHistoryTime2) {
        this.calledHistoryTime = calledHistoryTime2;
    }
}
