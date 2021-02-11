package com.goodocom.gocsdk.domain;

import android.bluetooth.BluetoothDevice;
import java.util.Objects;

public class GocBluetoothDevice {
    public BluetoothDevice device;
    public boolean isMain;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GocBluetoothDevice)) {
            return false;
        }
        GocBluetoothDevice that = (GocBluetoothDevice) o;
        if (this.isMain != that.isMain || !Objects.equals(this.device, that.device)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.device, Boolean.valueOf(this.isMain));
    }
}
