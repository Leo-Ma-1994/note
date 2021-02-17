package com.goodocom.gocsdk.manager;

public class Contacts {
    private String id;
    private boolean isChecked;
    private String name;
    private String phoneNumber;

    public boolean isChecked() {
        return this.isChecked;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id2) {
        this.id = id2;
    }

    public Contacts(String name2, String phoneNumber2, boolean isChecked2) {
        this.name = name2;
        this.phoneNumber = phoneNumber2;
        this.isChecked = isChecked2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber2) {
        this.phoneNumber = phoneNumber2;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean checked) {
        this.isChecked = checked;
    }

    public void changeChecked() {
        if (this.isChecked) {
            this.isChecked = false;
        } else {
            this.isChecked = true;
        }
    }
}
