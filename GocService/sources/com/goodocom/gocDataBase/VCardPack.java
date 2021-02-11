package com.goodocom.gocDataBase;

import android.database.Cursor;
import java.io.Serializable;
import java.util.Set;

public class VCardPack implements Serializable {
    private String HistoryDate;
    private String HistoryTime;
    private int _id;
    private String cellPhone_Address;
    private String firstName;
    private String first_CityNameAddress;
    private String first_CountryAddress;
    private String first_FederalStateAddress;
    private String first_StreetAddress;
    private String first_ZipCodeAddress;
    private String fullName;
    private String lastName;
    private String number;
    private Set<PhoneInfo> phoneNumbers;
    public String photoPath;
    private String second_CityNameAddress;
    private String second_CountryAddress;
    private String second_FederalStateAddress;
    private String second_StreetAddress;
    private String second_ZipCodeAddress;
    private String storageType;
    private String type;

    public int get_id() {
        return this._id;
    }

    public void setType(String type2) {
        this.type = type2;
    }

    public String getType() {
        return this.type;
    }

    public void set_id(int _id2) {
        this._id = _id2;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName2) {
        this.fullName = fullName2;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName2) {
        this.firstName = firstName2;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName2) {
        this.lastName = lastName2;
    }

    public String getFirst_StreetAddress() {
        return this.first_StreetAddress;
    }

    public void setFirst_StreetAddress(String first_StreetAddress2) {
        this.first_StreetAddress = first_StreetAddress2;
    }

    public String getFirst_CityNameAddress() {
        return this.first_CityNameAddress;
    }

    public void setFirst_CityNameAddress(String first_CityNameAddress2) {
        this.first_CityNameAddress = first_CityNameAddress2;
    }

    public String getFirst_FederalStateAddress() {
        return this.first_FederalStateAddress;
    }

    public void setFirst_FederalStateAddress(String first_FederalStateAddress2) {
        this.first_FederalStateAddress = first_FederalStateAddress2;
    }

    public String getFirst_ZipCodeAddress() {
        return this.first_ZipCodeAddress;
    }

    public void setFirst_ZipCodeAddress(String first_ZipCodeAddress2) {
        this.first_ZipCodeAddress = first_ZipCodeAddress2;
    }

    public String getFirst_CountryAddress() {
        return this.first_CountryAddress;
    }

    public void setFirst_CountryAddress(String first_CountryAddress2) {
        this.first_CountryAddress = first_CountryAddress2;
    }

    public String getSecond_StreetAddress() {
        return this.second_StreetAddress;
    }

    public void setSecond_StreetAddress(String second_StreetAddress2) {
        this.second_StreetAddress = second_StreetAddress2;
    }

    public String getSecond_CityNameAddress() {
        return this.second_CityNameAddress;
    }

    public void setSecond_CityNameAddress(String second_CityNameAddress2) {
        this.second_CityNameAddress = second_CityNameAddress2;
    }

    public String getSecond_FederalStateAddress() {
        return this.second_FederalStateAddress;
    }

    public void setSecond_FederalStateAddress(String second_FederalStateAddress2) {
        this.second_FederalStateAddress = second_FederalStateAddress2;
    }

    public String getSecond_ZipCodeAddress() {
        return this.second_ZipCodeAddress;
    }

    public void setSecond_ZipCodeAddress(String second_ZipCodeAddress2) {
        this.second_ZipCodeAddress = second_ZipCodeAddress2;
    }

    public String getSecond_CountryAddress() {
        return this.second_CountryAddress;
    }

    public void setSecond_CountryAddress(String second_CountryAddress2) {
        this.second_CountryAddress = second_CountryAddress2;
    }

    public String getCellPhone_Address() {
        return this.cellPhone_Address;
    }

    public void setCellPhone_Address(String cellPhone_Address2) {
        this.cellPhone_Address = cellPhone_Address2;
    }

    public Set<PhoneInfo> getPhoneNumbers() {
        return this.phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneInfo> phoneNumbers2) {
        this.phoneNumbers = phoneNumbers2;
    }

    public String getStorageType() {
        return this.storageType;
    }

    public void setStorageType(String storageType2) {
        this.storageType = storageType2;
    }

    public VCardPack() {
    }

    public VCardPack(Cursor cursor) {
        int idxId = cursor.getColumnIndex("_id");
        int idxFullName = cursor.getColumnIndex("FullName");
        int idxFirstName = cursor.getColumnIndex("FirstName");
        int idxLastName = cursor.getColumnIndex("LastName");
        int idxFirst_StreetAddress = cursor.getColumnIndex("First_StreetAddress");
        int idxFirst_CityNameAddress = cursor.getColumnIndex("First_CityNameAddress");
        int idxFirst_FederalStateAddress = cursor.getColumnIndex("First_FederalStateAddress");
        int idxFirst_ZipCodeAddress = cursor.getColumnIndex("First_ZipCodeAddress");
        int idxFirst_CountryAddress = cursor.getColumnIndex("First_CountryAddress");
        int idxSecond_StreetAddress = cursor.getColumnIndex("Second_StreetAddress");
        int idxSecond_CityNameAddress = cursor.getColumnIndex("Second_CityNameAddress");
        int idxSecond_FederalStateAddress = cursor.getColumnIndex("Second_FederalStateAddress");
        int idxSecond_ZipCodeAddress = cursor.getColumnIndex("Second_ZipCodeAddress");
        int idxSecond_CountryAddress = cursor.getColumnIndex("Second_CountryAddress");
        int idxCellPhoneAddress = cursor.getColumnIndex("CellPhone_Address");
        int idxStroageType = cursor.getColumnIndex("StorageType");
        set_id(cursor.getInt(idxId));
        setFullName(cursor.getString(idxFullName));
        setFirstName(cursor.getString(idxFirstName));
        setLastName(cursor.getString(idxLastName));
        setFirst_StreetAddress(cursor.getString(idxFirst_StreetAddress));
        setFirst_CityNameAddress(cursor.getString(idxFirst_CityNameAddress));
        setFirst_FederalStateAddress(cursor.getString(idxFirst_FederalStateAddress));
        setFirst_ZipCodeAddress(cursor.getString(idxFirst_ZipCodeAddress));
        setFirst_CountryAddress(cursor.getString(idxFirst_CountryAddress));
        setSecond_StreetAddress(cursor.getString(idxSecond_StreetAddress));
        setSecond_CityNameAddress(cursor.getString(idxSecond_CityNameAddress));
        setSecond_FederalStateAddress(cursor.getString(idxSecond_FederalStateAddress));
        setSecond_ZipCodeAddress(cursor.getString(idxSecond_ZipCodeAddress));
        setSecond_CountryAddress(cursor.getString(idxSecond_CountryAddress));
        setCellPhone_Address(cursor.getString(idxCellPhoneAddress));
        setStorageType(cursor.getString(idxStroageType));
    }

    public String getHistoryDate() {
        return this.HistoryDate;
    }

    public void setHistoryDate(String historyDate) {
        this.HistoryDate = historyDate;
    }

    public String getHistoryTime() {
        return this.HistoryTime;
    }

    public void setHistoryTime(String historyTime) {
        this.HistoryTime = historyTime;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number2) {
        this.number = number2;
    }
}
