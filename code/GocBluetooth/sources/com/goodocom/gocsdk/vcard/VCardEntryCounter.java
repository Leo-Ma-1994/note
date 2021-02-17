package com.goodocom.gocsdk.vcard;

public class VCardEntryCounter implements VCardInterpreter {
    private int mCount;

    public int getCount() {
        return this.mCount;
    }

    @Override // com.goodocom.gocsdk.vcard.VCardInterpreter
    public void onVCardStarted() {
    }

    @Override // com.goodocom.gocsdk.vcard.VCardInterpreter
    public void onVCardEnded() {
    }

    @Override // com.goodocom.gocsdk.vcard.VCardInterpreter
    public void onEntryStarted() {
    }

    @Override // com.goodocom.gocsdk.vcard.VCardInterpreter
    public void onEntryEnded() {
        this.mCount++;
    }

    @Override // com.goodocom.gocsdk.vcard.VCardInterpreter
    public void onPropertyCreated(VCardProperty property) {
    }
}
