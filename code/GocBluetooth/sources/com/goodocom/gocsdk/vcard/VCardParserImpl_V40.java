package com.goodocom.gocsdk.vcard;

import java.util.Set;

/* access modifiers changed from: package-private */
public class VCardParserImpl_V40 extends VCardParserImpl_V30 {
    public VCardParserImpl_V40() {
    }

    public VCardParserImpl_V40(int vcardType) {
        super(vcardType);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V30, com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public int getVersion() {
        return 2;
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V30, com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public String getVersionString() {
        return VCardConstants.VERSION_V40;
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V30, com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public String maybeUnescapeText(String text) {
        return unescapeText(text);
    }

    public static String unescapeText(String text) {
        StringBuilder builder = new StringBuilder();
        int length = text.length();
        int i = 0;
        while (i < length) {
            char ch = text.charAt(i);
            if (ch != '\\' || i >= length - 1) {
                builder.append(ch);
            } else {
                i++;
                char next_ch = text.charAt(i);
                if (next_ch == 'n' || next_ch == 'N') {
                    builder.append("\n");
                } else {
                    builder.append(next_ch);
                }
            }
            i++;
        }
        return builder.toString();
    }

    public static String unescapeCharacter(char ch) {
        if (ch == 'n' || ch == 'N') {
            return "\n";
        }
        return String.valueOf(ch);
    }

    /* access modifiers changed from: protected */
    @Override // com.goodocom.gocsdk.vcard.VCardParserImpl_V30, com.goodocom.gocsdk.vcard.VCardParserImpl_V21
    public Set<String> getKnownPropertyNameSet() {
        return VCardParser_V40.sKnownPropertyNameSet;
    }
}
