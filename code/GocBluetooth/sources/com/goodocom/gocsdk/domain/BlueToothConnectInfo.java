package com.goodocom.gocsdk.domain;

public class BlueToothConnectInfo {
    public String address;
    public String name;

    public boolean equals(Object obj) {
        if (obj instanceof BlueToothPairedInfo) {
            return this.address.equals(((BlueToothPairedInfo) obj).address);
        }
        return super.equals(obj);
    }
}
