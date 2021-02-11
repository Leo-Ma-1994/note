package com.goodocom.bttek.bt.bean;

public class GocBtInfo {
    private String Address;
    private String DriverVersion;
    private String FirmwareVersion;
    private String Profiles;
    private String StackVersion;
    private String Supplier;
    private String VersionInfo;

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public String getDriverVersion() {
        return this.DriverVersion;
    }

    public void setDriverVersion(String driverVersion) {
        this.DriverVersion = driverVersion;
    }

    public String getStackVersion() {
        return this.StackVersion;
    }

    public void setStackVersion(String stackVersion) {
        this.StackVersion = stackVersion;
    }

    public String getFirmwareVersion() {
        return this.FirmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.FirmwareVersion = firmwareVersion;
    }

    public String getSupplier() {
        return this.Supplier;
    }

    public void setSupplier(String supplier) {
        this.Supplier = supplier;
    }

    public String getProfiles() {
        return this.Profiles;
    }

    public void setProfiles(String profiles) {
        this.Profiles = profiles;
    }

    public String getVersionInfo() {
        return this.VersionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.VersionInfo = versionInfo;
    }
}
