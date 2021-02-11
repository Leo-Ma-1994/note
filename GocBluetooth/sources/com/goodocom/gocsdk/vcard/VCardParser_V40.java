package com.goodocom.gocsdk.vcard;

import com.goodocom.gocsdk.vcard.exception.VCardException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class VCardParser_V40 extends VCardParser {
    static final Set<String> sAcceptableEncoding = Collections.unmodifiableSet(new HashSet(Arrays.asList(VCardConstants.PARAM_ENCODING_8BIT, VCardConstants.PARAM_ENCODING_B)));
    static final Set<String> sKnownPropertyNameSet = Collections.unmodifiableSet(new HashSet(Arrays.asList(VCardConstants.PROPERTY_BEGIN, VCardConstants.PROPERTY_END, VCardConstants.PROPERTY_VERSION, "SOURCE", "KIND", VCardConstants.PROPERTY_FN, VCardConstants.PROPERTY_N, VCardConstants.PROPERTY_NICKNAME, VCardConstants.PROPERTY_PHOTO, VCardConstants.PROPERTY_BDAY, VCardConstants.PROPERTY_ANNIVERSARY, VCardConstants.PROPERTY_GENDER, VCardConstants.PROPERTY_ADR, VCardConstants.PROPERTY_TEL, VCardConstants.PROPERTY_EMAIL, VCardConstants.PROPERTY_IMPP, "LANG", "TZ", "GEO", VCardConstants.PROPERTY_TITLE, VCardConstants.PROPERTY_ROLE, VCardConstants.PROPERTY_LOGO, VCardConstants.PROPERTY_ORG, "MEMBER", VCardConstants.PROPERTY_RELATED, VCardConstants.PROPERTY_CATEGORIES, VCardConstants.PROPERTY_NOTE, VCardConstants.PROPERTY_PRODID, VCardConstants.PROPERTY_REV, VCardConstants.PROPERTY_SOUND, "UID", VCardConstants.PROPERTY_CLIENTPIDMAP, VCardConstants.PROPERTY_URL, "KEY", VCardConstants.PROPERTY_FBURL, "CALENDRURI", VCardConstants.PROPERTY_CALURI, VCardConstants.PROPERTY_XML)));
    private final VCardParserImpl_V40 mVCardParserImpl;

    public VCardParser_V40() {
        this.mVCardParserImpl = new VCardParserImpl_V40();
    }

    public VCardParser_V40(int vcardType) {
        this.mVCardParserImpl = new VCardParserImpl_V40(vcardType);
    }

    @Override // com.goodocom.gocsdk.vcard.VCardParser
    public void addInterpreter(VCardInterpreter interpreter) {
        this.mVCardParserImpl.addInterpreter(interpreter);
    }

    @Override // com.goodocom.gocsdk.vcard.VCardParser
    public void parse(InputStream is) throws IOException, VCardException {
        this.mVCardParserImpl.parse(is);
    }

    @Override // com.goodocom.gocsdk.vcard.VCardParser
    public void parseOne(InputStream is) throws IOException, VCardException {
        this.mVCardParserImpl.parseOne(is);
    }

    @Override // com.goodocom.gocsdk.vcard.VCardParser
    public void cancel() {
        this.mVCardParserImpl.cancel();
    }
}
