package com.goodocom.gocsdk.domain;

public class BlueToothPairedInfo extends BlueToothInfo {
    public String address;
    public int index;
    public boolean isConnected;
    public String name;

    public boolean equals(Object obj) {
        if (obj instanceof BlueToothPairedInfo) {
            return this.address.equals(((BlueToothPairedInfo) obj).address);
        }
        return super.equals(obj);
    }
}
