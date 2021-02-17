package com.goodocom.gocDataBase;

import java.io.Serializable;
import java.util.List;

public class VCardList implements Serializable {
    private List<VCardPack> vcardPacks;

    public List<VCardPack> getVcardPacks() {
        return this.vcardPacks;
    }

    public void setVcardPacks(List<VCardPack> vcardPacks2) {
        this.vcardPacks = vcardPacks2;
    }
}
