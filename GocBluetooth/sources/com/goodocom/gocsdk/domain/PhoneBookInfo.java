package com.goodocom.gocsdk.domain;

import java.util.Objects;

public class PhoneBookInfo {
    public String name = null;
    public String num = null;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhoneBookInfo)) {
            return false;
        }
        PhoneBookInfo that = (PhoneBookInfo) o;
        if (!Objects.equals(this.name, that.name) || !Objects.equals(this.num, that.num)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.name, this.num);
    }
}
