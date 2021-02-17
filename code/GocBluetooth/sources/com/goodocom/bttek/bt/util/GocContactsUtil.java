package com.goodocom.bttek.bt.util;

import com.goodocom.bttek.bt.bean.Contacts;
import java.util.List;

public class GocContactsUtil {
    private static GocContactsUtil mGocContactsUtil = new GocContactsUtil();
    private List<Contacts> contactsList;

    public static GocContactsUtil getInstance() {
        return mGocContactsUtil;
    }

    public List<Contacts> getContactsList() {
        return this.contactsList;
    }

    public void setContactsList(List<Contacts> contactsList2) {
        new StringBuffer();
        if (contactsList2 != null && contactsList2.size() > 0) {
            for (int i = 0; i < contactsList2.size(); i++) {
                List<String> pinyinList = CharacterParser.getInstance().getSelling(contactsList2.get(i).getName());
                if (pinyinList != null && pinyinList.size() > 0) {
                    contactsList2.get(i).setPinyinList(pinyinList);
                }
            }
        }
        this.contactsList = contactsList2;
    }
}
