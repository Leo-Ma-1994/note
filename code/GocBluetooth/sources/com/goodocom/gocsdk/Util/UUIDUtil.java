package com.goodocom.gocsdk.Util;

import java.util.UUID;

public class UUIDUtil {
    public static void main(String[] args) {
        System.out.println(get4UUID());
        System.out.println(get8UUID());
        System.out.println(get12UUID());
        System.out.println(get16UUID());
        System.out.println(get20UUID());
        System.out.println(get24UUID());
        System.out.println(get32UUID());
    }

    public static String get4UUID() {
        return UUID.randomUUID().toString().split("-")[1];
    }

    public static String get8UUID() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    public static String get12UUID() {
        String[] idd = UUID.randomUUID().toString().split("-");
        return idd[0] + idd[1];
    }

    public static String get16UUID() {
        String[] idd = UUID.randomUUID().toString().split("-");
        return idd[0] + idd[1] + idd[2];
    }

    public static String get20UUID() {
        String[] idd = UUID.randomUUID().toString().split("-");
        return idd[0] + idd[1] + idd[2] + idd[3];
    }

    public static String get24UUID() {
        String[] idd = UUID.randomUUID().toString().split("-");
        return idd[0] + idd[1] + idd[4];
    }

    public static String get32UUID() {
        String[] idd = UUID.randomUUID().toString().split("-");
        return idd[0] + idd[1] + idd[2] + idd[3] + idd[4];
    }
}
