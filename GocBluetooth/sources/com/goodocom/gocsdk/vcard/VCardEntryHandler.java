package com.goodocom.gocsdk.vcard;

public interface VCardEntryHandler {
    void onEnd();

    void onEntryCreated(VCardEntry vCardEntry);

    void onStart();
}
