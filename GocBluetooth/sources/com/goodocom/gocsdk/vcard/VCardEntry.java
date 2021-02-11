package com.goodocom.gocsdk.vcard;

import android.accounts.Account;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.goodocom.gocsdk.vcard.VCardConstants;
import com.goodocom.gocsdk.vcard.VCardUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class VCardEntry {
    private static final int DEFAULT_ORGANIZATION_TYPE = 1;
    private static final String LOG_TAG = "vCard";
    private static final List<String> sEmptyList = Collections.unmodifiableList(new ArrayList(0));
    private static final Map<String, Integer> sImMap = new HashMap();
    private final Account mAccount;
    private List<AndroidCustomData> mAndroidCustomDataList;
    private AnniversaryData mAnniversary;
    private BirthdayData mBirthday;
    private List<VCardEntry> mChildren;
    private List<EmailData> mEmailList;
    private List<ImData> mImList;
    private final NameData mNameData;
    private List<NicknameData> mNicknameList;
    private List<NoteData> mNoteList;
    private List<OrganizationData> mOrganizationList;
    private List<PhoneData> mPhoneList;
    private List<PhotoData> mPhotoList;
    private List<PostalData> mPostalList;
    private List<SipData> mSipList;
    private List<Pair<String, String>> mUnknownXData;
    private final int mVCardType;
    private List<WebsiteData> mWebsiteList;

    public interface EntryElement {
        void constructInsertOperation(List<ContentProviderOperation> list, int i);

        EntryLabel getEntryLabel();

        boolean isEmpty();
    }

    public interface EntryElementIterator {
        boolean onElement(EntryElement entryElement);

        void onElementGroupEnded();

        void onElementGroupStarted(EntryLabel entryLabel);

        void onIterationEnded();

        void onIterationStarted();
    }

    public enum EntryLabel {
        NAME,
        PHONE,
        EMAIL,
        POSTAL_ADDRESS,
        ORGANIZATION,
        IM,
        PHOTO,
        WEBSITE,
        SIP,
        NICKNAME,
        NOTE,
        BIRTHDAY,
        ANNIVERSARY,
        ANDROID_CUSTOM
    }

    static {
        sImMap.put(VCardConstants.PROPERTY_X_AIM, 0);
        sImMap.put(VCardConstants.PROPERTY_X_MSN, 1);
        sImMap.put(VCardConstants.PROPERTY_X_YAHOO, 2);
        sImMap.put(VCardConstants.PROPERTY_X_ICQ, 6);
        sImMap.put(VCardConstants.PROPERTY_X_JABBER, 7);
        sImMap.put(VCardConstants.PROPERTY_X_SKYPE_USERNAME, 3);
        sImMap.put(VCardConstants.PROPERTY_X_GOOGLE_TALK, 5);
        sImMap.put(VCardConstants.ImportOnly.PROPERTY_X_GOOGLE_TALK_WITH_SPACE, 5);
    }

    public static class NameData implements EntryElement {
        public String displayName;
        private String mFamily;
        private String mFormatted;
        private String mGiven;
        private String mMiddle;
        private String mPhoneticFamily;
        private String mPhoneticGiven;
        private String mPhoneticMiddle;
        private String mPrefix;
        private String mSortString;
        private String mSuffix;

        public boolean emptyStructuredName() {
            return TextUtils.isEmpty(this.mFamily) && TextUtils.isEmpty(this.mGiven) && TextUtils.isEmpty(this.mMiddle) && TextUtils.isEmpty(this.mPrefix) && TextUtils.isEmpty(this.mSuffix);
        }

        public boolean emptyPhoneticStructuredName() {
            return TextUtils.isEmpty(this.mPhoneticFamily) && TextUtils.isEmpty(this.mPhoneticGiven) && TextUtils.isEmpty(this.mPhoneticMiddle);
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/name");
            if (!TextUtils.isEmpty(this.mGiven)) {
                builder.withValue("data2", this.mGiven);
            }
            if (!TextUtils.isEmpty(this.mFamily)) {
                builder.withValue("data3", this.mFamily);
            }
            if (!TextUtils.isEmpty(this.mMiddle)) {
                builder.withValue("data5", this.mMiddle);
            }
            if (!TextUtils.isEmpty(this.mPrefix)) {
                builder.withValue("data4", this.mPrefix);
            }
            if (!TextUtils.isEmpty(this.mSuffix)) {
                builder.withValue("data6", this.mSuffix);
            }
            boolean phoneticNameSpecified = false;
            if (!TextUtils.isEmpty(this.mPhoneticGiven)) {
                builder.withValue("data7", this.mPhoneticGiven);
                phoneticNameSpecified = true;
            }
            if (!TextUtils.isEmpty(this.mPhoneticFamily)) {
                builder.withValue("data9", this.mPhoneticFamily);
                phoneticNameSpecified = true;
            }
            if (!TextUtils.isEmpty(this.mPhoneticMiddle)) {
                builder.withValue("data8", this.mPhoneticMiddle);
                phoneticNameSpecified = true;
            }
            if (!phoneticNameSpecified) {
                builder.withValue("data7", this.mSortString);
            }
            builder.withValue("data1", this.displayName);
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mFamily) && TextUtils.isEmpty(this.mMiddle) && TextUtils.isEmpty(this.mGiven) && TextUtils.isEmpty(this.mPrefix) && TextUtils.isEmpty(this.mSuffix) && TextUtils.isEmpty(this.mFormatted) && TextUtils.isEmpty(this.mPhoneticFamily) && TextUtils.isEmpty(this.mPhoneticMiddle) && TextUtils.isEmpty(this.mPhoneticGiven) && TextUtils.isEmpty(this.mSortString);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NameData)) {
                return false;
            }
            NameData nameData = (NameData) obj;
            if (!TextUtils.equals(this.mFamily, nameData.mFamily) || !TextUtils.equals(this.mMiddle, nameData.mMiddle) || !TextUtils.equals(this.mGiven, nameData.mGiven) || !TextUtils.equals(this.mPrefix, nameData.mPrefix) || !TextUtils.equals(this.mSuffix, nameData.mSuffix) || !TextUtils.equals(this.mFormatted, nameData.mFormatted) || !TextUtils.equals(this.mPhoneticFamily, nameData.mPhoneticFamily) || !TextUtils.equals(this.mPhoneticMiddle, nameData.mPhoneticMiddle) || !TextUtils.equals(this.mPhoneticGiven, nameData.mPhoneticGiven) || !TextUtils.equals(this.mSortString, nameData.mSortString)) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            String[] hashTargets = {this.mFamily, this.mMiddle, this.mGiven, this.mPrefix, this.mSuffix, this.mFormatted, this.mPhoneticFamily, this.mPhoneticMiddle, this.mPhoneticGiven, this.mSortString};
            int length = hashTargets.length;
            int hash = 0;
            for (int hash2 = 0; hash2 < length; hash2++) {
                String hashTarget = hashTargets[hash2];
                hash = (hash * 31) + (hashTarget != null ? hashTarget.hashCode() : 0);
            }
            return hash;
        }

        public String toString() {
            return String.format("family: %s, given: %s, middle: %s, prefix: %s, suffix: %s", this.mFamily, this.mGiven, this.mMiddle, this.mPrefix, this.mSuffix);
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public final EntryLabel getEntryLabel() {
            return EntryLabel.NAME;
        }

        public String getFamily() {
            return this.mFamily;
        }

        public String getMiddle() {
            return this.mMiddle;
        }

        public String getGiven() {
            return this.mGiven;
        }

        public String getPrefix() {
            return this.mPrefix;
        }

        public String getSuffix() {
            return this.mSuffix;
        }

        public String getFormatted() {
            return this.mFormatted;
        }

        public String getSortString() {
            return this.mSortString;
        }

        public void setFamily(String family) {
            this.mFamily = family;
        }

        public void setMiddle(String middle) {
            this.mMiddle = middle;
        }

        public void setGiven(String given) {
            this.mGiven = given;
        }

        public void setPrefix(String prefix) {
            this.mPrefix = prefix;
        }

        public void setSuffix(String suffix) {
            this.mSuffix = suffix;
        }
    }

    public static class PhoneData implements EntryElement {
        private boolean mIsPrimary;
        private final String mLabel;
        private final String mNumber;
        private final int mType;

        public PhoneData(String data, int type, String label, boolean isPrimary) {
            this.mNumber = data;
            this.mType = type;
            this.mLabel = label;
            this.mIsPrimary = isPrimary;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/phone_v2");
            builder.withValue("data2", Integer.valueOf(this.mType));
            if (this.mType == 0) {
                builder.withValue("data3", this.mLabel);
            }
            builder.withValue("data1", this.mNumber);
            if (this.mIsPrimary) {
                builder.withValue("is_primary", 1);
            }
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mNumber);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PhoneData)) {
                return false;
            }
            PhoneData phoneData = (PhoneData) obj;
            if (this.mType != phoneData.mType || !TextUtils.equals(this.mNumber, phoneData.mNumber) || !TextUtils.equals(this.mLabel, phoneData.mLabel) || this.mIsPrimary != phoneData.mIsPrimary) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = this.mType * 31;
            String str = this.mNumber;
            int i2 = 0;
            int hash = (i + (str != null ? str.hashCode() : 0)) * 31;
            String str2 = this.mLabel;
            if (str2 != null) {
                i2 = str2.hashCode();
            }
            return ((hash + i2) * 31) + (this.mIsPrimary ? 1231 : 1237);
        }

        public String toString() {
            return String.format("type: %d, data: %s, label: %s, isPrimary: %s", Integer.valueOf(this.mType), this.mNumber, this.mLabel, Boolean.valueOf(this.mIsPrimary));
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public final EntryLabel getEntryLabel() {
            return EntryLabel.PHONE;
        }

        public String getNumber() {
            return this.mNumber;
        }

        public int getType() {
            return this.mType;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public boolean isPrimary() {
            return this.mIsPrimary;
        }
    }

    public static class EmailData implements EntryElement {
        private final String mAddress;
        private final boolean mIsPrimary;
        private final String mLabel;
        private final int mType;

        public EmailData(String data, int type, String label, boolean isPrimary) {
            this.mType = type;
            this.mAddress = data;
            this.mLabel = label;
            this.mIsPrimary = isPrimary;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/email_v2");
            builder.withValue("data2", Integer.valueOf(this.mType));
            if (this.mType == 0) {
                builder.withValue("data3", this.mLabel);
            }
            builder.withValue("data1", this.mAddress);
            if (this.mIsPrimary) {
                builder.withValue("is_primary", 1);
            }
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mAddress);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof EmailData)) {
                return false;
            }
            EmailData emailData = (EmailData) obj;
            if (this.mType != emailData.mType || !TextUtils.equals(this.mAddress, emailData.mAddress) || !TextUtils.equals(this.mLabel, emailData.mLabel) || this.mIsPrimary != emailData.mIsPrimary) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = this.mType * 31;
            String str = this.mAddress;
            int i2 = 0;
            int hash = (i + (str != null ? str.hashCode() : 0)) * 31;
            String str2 = this.mLabel;
            if (str2 != null) {
                i2 = str2.hashCode();
            }
            return ((hash + i2) * 31) + (this.mIsPrimary ? 1231 : 1237);
        }

        public String toString() {
            return String.format("type: %d, data: %s, label: %s, isPrimary: %s", Integer.valueOf(this.mType), this.mAddress, this.mLabel, Boolean.valueOf(this.mIsPrimary));
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public final EntryLabel getEntryLabel() {
            return EntryLabel.EMAIL;
        }

        public String getAddress() {
            return this.mAddress;
        }

        public int getType() {
            return this.mType;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public boolean isPrimary() {
            return this.mIsPrimary;
        }
    }

    public static class PostalData implements EntryElement {
        private static final int ADDR_MAX_DATA_SIZE = 7;
        private final String mCountry;
        private final String mExtendedAddress;
        private boolean mIsPrimary;
        private final String mLabel;
        private final String mLocalty;
        private final String mPobox;
        private final String mPostalCode;
        private final String mRegion;
        private final String mStreet;
        private final int mType;
        private int mVCardType;

        public PostalData(String pobox, String extendedAddress, String street, String localty, String region, String postalCode, String country, int type, String label, boolean isPrimary, int vcardType) {
            this.mType = type;
            this.mPobox = pobox;
            this.mExtendedAddress = extendedAddress;
            this.mStreet = street;
            this.mLocalty = localty;
            this.mRegion = region;
            this.mPostalCode = postalCode;
            this.mCountry = country;
            this.mLabel = label;
            this.mIsPrimary = isPrimary;
            this.mVCardType = vcardType;
        }

        public static PostalData constructPostalData(List<String> propValueList, int type, String label, boolean isPrimary, int vcardType) {
            String[] dataArray = new String[7];
            int size = propValueList.size();
            if (size > 7) {
                size = 7;
            }
            int i = 0;
            for (String addressElement : propValueList) {
                dataArray[i] = addressElement;
                i++;
                if (i >= size) {
                    break;
                }
            }
            while (i < 7) {
                dataArray[i] = null;
                i++;
            }
            return new PostalData(dataArray[0], dataArray[1], dataArray[2], dataArray[3], dataArray[4], dataArray[5], dataArray[6], type, label, isPrimary, vcardType);
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            String streetString;
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/postal-address_v2");
            builder.withValue("data2", Integer.valueOf(this.mType));
            if (this.mType == 0) {
                builder.withValue("data3", this.mLabel);
            }
            if (TextUtils.isEmpty(this.mStreet)) {
                if (TextUtils.isEmpty(this.mExtendedAddress)) {
                    streetString = null;
                } else {
                    streetString = this.mExtendedAddress;
                }
            } else if (TextUtils.isEmpty(this.mExtendedAddress)) {
                streetString = this.mStreet;
            } else {
                streetString = this.mStreet + " " + this.mExtendedAddress;
            }
            builder.withValue("data5", this.mPobox);
            builder.withValue("data4", streetString);
            builder.withValue("data7", this.mLocalty);
            builder.withValue("data8", this.mRegion);
            builder.withValue("data9", this.mPostalCode);
            builder.withValue("data10", this.mCountry);
            builder.withValue("data1", getFormattedAddress(this.mVCardType));
            if (this.mIsPrimary) {
                builder.withValue("is_primary", 1);
            }
            operationList.add(builder.build());
        }

        public String getFormattedAddress(int vcardType) {
            StringBuilder builder = new StringBuilder();
            boolean empty = true;
            String[] dataArray = {this.mPobox, this.mExtendedAddress, this.mStreet, this.mLocalty, this.mRegion, this.mPostalCode, this.mCountry};
            if (VCardConfig.isJapaneseDevice(vcardType)) {
                for (int i = 6; i >= 0; i--) {
                    String addressPart = dataArray[i];
                    if (!TextUtils.isEmpty(addressPart)) {
                        if (!empty) {
                            builder.append(' ');
                        } else {
                            empty = false;
                        }
                        builder.append(addressPart);
                    }
                }
            } else {
                for (int i2 = 0; i2 < 7; i2++) {
                    String addressPart2 = dataArray[i2];
                    if (!TextUtils.isEmpty(addressPart2)) {
                        if (!empty) {
                            builder.append(' ');
                        } else {
                            empty = false;
                        }
                        builder.append(addressPart2);
                    }
                }
            }
            return builder.toString().trim();
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mPobox) && TextUtils.isEmpty(this.mExtendedAddress) && TextUtils.isEmpty(this.mStreet) && TextUtils.isEmpty(this.mLocalty) && TextUtils.isEmpty(this.mRegion) && TextUtils.isEmpty(this.mPostalCode) && TextUtils.isEmpty(this.mCountry);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PostalData)) {
                return false;
            }
            PostalData postalData = (PostalData) obj;
            int i = this.mType;
            if (i != postalData.mType || ((i == 0 && !TextUtils.equals(this.mLabel, postalData.mLabel)) || this.mIsPrimary != postalData.mIsPrimary || !TextUtils.equals(this.mPobox, postalData.mPobox) || !TextUtils.equals(this.mExtendedAddress, postalData.mExtendedAddress) || !TextUtils.equals(this.mStreet, postalData.mStreet) || !TextUtils.equals(this.mLocalty, postalData.mLocalty) || !TextUtils.equals(this.mRegion, postalData.mRegion) || !TextUtils.equals(this.mPostalCode, postalData.mPostalCode) || !TextUtils.equals(this.mCountry, postalData.mCountry))) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = this.mType * 31;
            String str = this.mLabel;
            int hash = ((i + (str != null ? str.hashCode() : 0)) * 31) + (this.mIsPrimary ? 1231 : 1237);
            String[] hashTargets = {this.mPobox, this.mExtendedAddress, this.mStreet, this.mLocalty, this.mRegion, this.mPostalCode, this.mCountry};
            int length = hashTargets.length;
            int hash2 = hash;
            for (int hash3 = 0; hash3 < length; hash3++) {
                String hashTarget = hashTargets[hash3];
                hash2 = (hash2 * 31) + (hashTarget != null ? hashTarget.hashCode() : 0);
            }
            return hash2;
        }

        public String toString() {
            return String.format("type: %d, label: %s, isPrimary: %s, pobox: %s, extendedAddress: %s, street: %s, localty: %s, region: %s, postalCode %s, country: %s", Integer.valueOf(this.mType), this.mLabel, Boolean.valueOf(this.mIsPrimary), this.mPobox, this.mExtendedAddress, this.mStreet, this.mLocalty, this.mRegion, this.mPostalCode, this.mCountry);
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public final EntryLabel getEntryLabel() {
            return EntryLabel.POSTAL_ADDRESS;
        }

        public String getPobox() {
            return this.mPobox;
        }

        public String getExtendedAddress() {
            return this.mExtendedAddress;
        }

        public String getStreet() {
            return this.mStreet;
        }

        public String getLocalty() {
            return this.mLocalty;
        }

        public String getRegion() {
            return this.mRegion;
        }

        public String getPostalCode() {
            return this.mPostalCode;
        }

        public String getCountry() {
            return this.mCountry;
        }

        public int getType() {
            return this.mType;
        }

        public String getLabel() {
            return this.mLabel;
        }

        public boolean isPrimary() {
            return this.mIsPrimary;
        }
    }

    public static class OrganizationData implements EntryElement {
        private String mDepartmentName;
        private boolean mIsPrimary;
        private String mOrganizationName;
        private final String mPhoneticName;
        private String mTitle;
        private final int mType;

        public OrganizationData(String organizationName, String departmentName, String titleName, String phoneticName, int type, boolean isPrimary) {
            this.mType = type;
            this.mOrganizationName = organizationName;
            this.mDepartmentName = departmentName;
            this.mTitle = titleName;
            this.mPhoneticName = phoneticName;
            this.mIsPrimary = isPrimary;
        }

        public String getFormattedString() {
            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(this.mOrganizationName)) {
                builder.append(this.mOrganizationName);
            }
            if (!TextUtils.isEmpty(this.mDepartmentName)) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }
                builder.append(this.mDepartmentName);
            }
            if (!TextUtils.isEmpty(this.mTitle)) {
                if (builder.length() > 0) {
                    builder.append(", ");
                }
                builder.append(this.mTitle);
            }
            return builder.toString();
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/organization");
            builder.withValue("data2", Integer.valueOf(this.mType));
            String str = this.mOrganizationName;
            if (str != null) {
                builder.withValue("data1", str);
            }
            String str2 = this.mDepartmentName;
            if (str2 != null) {
                builder.withValue("data5", str2);
            }
            String str3 = this.mTitle;
            if (str3 != null) {
                builder.withValue("data4", str3);
            }
            String str4 = this.mPhoneticName;
            if (str4 != null) {
                builder.withValue("data8", str4);
            }
            if (this.mIsPrimary) {
                builder.withValue("is_primary", 1);
            }
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mOrganizationName) && TextUtils.isEmpty(this.mDepartmentName) && TextUtils.isEmpty(this.mTitle) && TextUtils.isEmpty(this.mPhoneticName);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof OrganizationData)) {
                return false;
            }
            OrganizationData organization = (OrganizationData) obj;
            if (this.mType != organization.mType || !TextUtils.equals(this.mOrganizationName, organization.mOrganizationName) || !TextUtils.equals(this.mDepartmentName, organization.mDepartmentName) || !TextUtils.equals(this.mTitle, organization.mTitle) || this.mIsPrimary != organization.mIsPrimary) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = this.mType * 31;
            String str = this.mOrganizationName;
            int i2 = 0;
            int hash = (i + (str != null ? str.hashCode() : 0)) * 31;
            String str2 = this.mDepartmentName;
            int hash2 = (hash + (str2 != null ? str2.hashCode() : 0)) * 31;
            String str3 = this.mTitle;
            if (str3 != null) {
                i2 = str3.hashCode();
            }
            return ((hash2 + i2) * 31) + (this.mIsPrimary ? 1231 : 1237);
        }

        public String toString() {
            return String.format("type: %d, organization: %s, department: %s, title: %s, isPrimary: %s", Integer.valueOf(this.mType), this.mOrganizationName, this.mDepartmentName, this.mTitle, Boolean.valueOf(this.mIsPrimary));
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public final EntryLabel getEntryLabel() {
            return EntryLabel.ORGANIZATION;
        }

        public String getOrganizationName() {
            return this.mOrganizationName;
        }

        public String getDepartmentName() {
            return this.mDepartmentName;
        }

        public String getTitle() {
            return this.mTitle;
        }

        public String getPhoneticName() {
            return this.mPhoneticName;
        }

        public int getType() {
            return this.mType;
        }

        public boolean isPrimary() {
            return this.mIsPrimary;
        }
    }

    public static class ImData implements EntryElement {
        private final String mAddress;
        private final String mCustomProtocol;
        private final boolean mIsPrimary;
        private final int mProtocol;
        private final int mType;

        public ImData(int protocol, String customProtocol, String address, int type, boolean isPrimary) {
            this.mProtocol = protocol;
            this.mCustomProtocol = customProtocol;
            this.mType = type;
            this.mAddress = address;
            this.mIsPrimary = isPrimary;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/im");
            builder.withValue("data2", Integer.valueOf(this.mType));
            builder.withValue("data5", Integer.valueOf(this.mProtocol));
            builder.withValue("data1", this.mAddress);
            if (this.mProtocol == -1) {
                builder.withValue("data6", this.mCustomProtocol);
            }
            if (this.mIsPrimary) {
                builder.withValue("is_primary", 1);
            }
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mAddress);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ImData)) {
                return false;
            }
            ImData imData = (ImData) obj;
            if (this.mType == imData.mType && this.mProtocol == imData.mProtocol && TextUtils.equals(this.mCustomProtocol, imData.mCustomProtocol) && TextUtils.equals(this.mAddress, imData.mAddress) && this.mIsPrimary == imData.mIsPrimary) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int hash = ((this.mType * 31) + this.mProtocol) * 31;
            String str = this.mCustomProtocol;
            int i = 0;
            int hash2 = (hash + (str != null ? str.hashCode() : 0)) * 31;
            String str2 = this.mAddress;
            if (str2 != null) {
                i = str2.hashCode();
            }
            return ((hash2 + i) * 31) + (this.mIsPrimary ? 1231 : 1237);
        }

        public String toString() {
            return String.format("type: %d, protocol: %d, custom_protcol: %s, data: %s, isPrimary: %s", Integer.valueOf(this.mType), Integer.valueOf(this.mProtocol), this.mCustomProtocol, this.mAddress, Boolean.valueOf(this.mIsPrimary));
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public final EntryLabel getEntryLabel() {
            return EntryLabel.IM;
        }

        public String getAddress() {
            return this.mAddress;
        }

        public int getProtocol() {
            return this.mProtocol;
        }

        public String getCustomProtocol() {
            return this.mCustomProtocol;
        }

        public int getType() {
            return this.mType;
        }

        public boolean isPrimary() {
            return this.mIsPrimary;
        }
    }

    public static class PhotoData implements EntryElement {
        private final byte[] mBytes;
        private final String mFormat;
        private Integer mHashCode = null;
        private final boolean mIsPrimary;

        public PhotoData(String format, byte[] photoBytes, boolean isPrimary) {
            this.mFormat = format;
            this.mBytes = photoBytes;
            this.mIsPrimary = isPrimary;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/photo");
            builder.withValue("data15", this.mBytes);
            if (this.mIsPrimary) {
                builder.withValue("is_primary", 1);
            }
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            byte[] bArr = this.mBytes;
            return bArr == null || bArr.length == 0;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PhotoData)) {
                return false;
            }
            PhotoData photoData = (PhotoData) obj;
            if (!TextUtils.equals(this.mFormat, photoData.mFormat) || !Arrays.equals(this.mBytes, photoData.mBytes) || this.mIsPrimary != photoData.mIsPrimary) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            Integer num = this.mHashCode;
            if (num != null) {
                return num.intValue();
            }
            String str = this.mFormat;
            int hash = (str != null ? str.hashCode() : 0) * 31;
            byte[] bArr = this.mBytes;
            if (bArr != null) {
                for (byte b : bArr) {
                    hash += b;
                }
            }
            int hash2 = (hash * 31) + (this.mIsPrimary ? 1231 : 1237);
            this.mHashCode = Integer.valueOf(hash2);
            return hash2;
        }

        public String toString() {
            return String.format("format: %s: size: %d, isPrimary: %s", this.mFormat, Integer.valueOf(this.mBytes.length), Boolean.valueOf(this.mIsPrimary));
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public final EntryLabel getEntryLabel() {
            return EntryLabel.PHOTO;
        }

        public String getFormat() {
            return this.mFormat;
        }

        public byte[] getBytes() {
            return this.mBytes;
        }

        public boolean isPrimary() {
            return this.mIsPrimary;
        }
    }

    public static class NicknameData implements EntryElement {
        private final String mNickname;

        public NicknameData(String nickname) {
            this.mNickname = nickname;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/nickname");
            builder.withValue("data2", 1);
            builder.withValue("data1", this.mNickname);
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mNickname);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof NicknameData)) {
                return false;
            }
            return TextUtils.equals(this.mNickname, ((NicknameData) obj).mNickname);
        }

        public int hashCode() {
            String str = this.mNickname;
            if (str != null) {
                return str.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "nickname: " + this.mNickname;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public EntryLabel getEntryLabel() {
            return EntryLabel.NICKNAME;
        }

        public String getNickname() {
            return this.mNickname;
        }
    }

    public static class NoteData implements EntryElement {
        public final String mNote;

        public NoteData(String note) {
            this.mNote = note;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/note");
            builder.withValue("data1", this.mNote);
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mNote);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof NoteData)) {
                return false;
            }
            return TextUtils.equals(this.mNote, ((NoteData) obj).mNote);
        }

        public int hashCode() {
            String str = this.mNote;
            if (str != null) {
                return str.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "note: " + this.mNote;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public EntryLabel getEntryLabel() {
            return EntryLabel.NOTE;
        }

        public String getNote() {
            return this.mNote;
        }
    }

    public static class WebsiteData implements EntryElement {
        private final String mWebsite;

        public WebsiteData(String website) {
            this.mWebsite = website;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/website");
            builder.withValue("data1", this.mWebsite);
            builder.withValue("data2", 1);
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mWebsite);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof WebsiteData)) {
                return false;
            }
            return TextUtils.equals(this.mWebsite, ((WebsiteData) obj).mWebsite);
        }

        public int hashCode() {
            String str = this.mWebsite;
            if (str != null) {
                return str.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "website: " + this.mWebsite;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public EntryLabel getEntryLabel() {
            return EntryLabel.WEBSITE;
        }

        public String getWebsite() {
            return this.mWebsite;
        }
    }

    public static class BirthdayData implements EntryElement {
        private final String mBirthday;

        public BirthdayData(String birthday) {
            this.mBirthday = birthday;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/contact_event");
            builder.withValue("data1", this.mBirthday);
            builder.withValue("data2", 3);
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mBirthday);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BirthdayData)) {
                return false;
            }
            return TextUtils.equals(this.mBirthday, ((BirthdayData) obj).mBirthday);
        }

        public int hashCode() {
            String str = this.mBirthday;
            if (str != null) {
                return str.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "birthday: " + this.mBirthday;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public EntryLabel getEntryLabel() {
            return EntryLabel.BIRTHDAY;
        }

        public String getBirthday() {
            return this.mBirthday;
        }
    }

    public static class AnniversaryData implements EntryElement {
        private final String mAnniversary;

        public AnniversaryData(String anniversary) {
            this.mAnniversary = anniversary;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/contact_event");
            builder.withValue("data1", this.mAnniversary);
            builder.withValue("data2", 1);
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mAnniversary);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AnniversaryData)) {
                return false;
            }
            return TextUtils.equals(this.mAnniversary, ((AnniversaryData) obj).mAnniversary);
        }

        public int hashCode() {
            String str = this.mAnniversary;
            if (str != null) {
                return str.hashCode();
            }
            return 0;
        }

        public String toString() {
            return "anniversary: " + this.mAnniversary;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public EntryLabel getEntryLabel() {
            return EntryLabel.ANNIVERSARY;
        }

        public String getAnniversary() {
            return this.mAnniversary;
        }
    }

    public static class SipData implements EntryElement {
        private final String mAddress;
        private final boolean mIsPrimary;
        private final String mLabel;
        private final int mType;

        public SipData(String rawSip, int type, String label, boolean isPrimary) {
            if (rawSip.startsWith("sip:")) {
                this.mAddress = rawSip.substring(4);
            } else {
                this.mAddress = rawSip;
            }
            this.mType = type;
            this.mLabel = label;
            this.mIsPrimary = isPrimary;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", "vnd.android.cursor.item/sip_address");
            builder.withValue("data1", this.mAddress);
            builder.withValue("data2", Integer.valueOf(this.mType));
            if (this.mType == 0) {
                builder.withValue("data3", this.mLabel);
            }
            boolean z = this.mIsPrimary;
            if (z) {
                builder.withValue("is_primary", Boolean.valueOf(z));
            }
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            return TextUtils.isEmpty(this.mAddress);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof SipData)) {
                return false;
            }
            SipData sipData = (SipData) obj;
            if (this.mType != sipData.mType || !TextUtils.equals(this.mLabel, sipData.mLabel) || !TextUtils.equals(this.mAddress, sipData.mAddress) || this.mIsPrimary != sipData.mIsPrimary) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            int i = this.mType * 31;
            String str = this.mLabel;
            int i2 = 0;
            int hash = (i + (str != null ? str.hashCode() : 0)) * 31;
            String str2 = this.mAddress;
            if (str2 != null) {
                i2 = str2.hashCode();
            }
            return ((hash + i2) * 31) + (this.mIsPrimary ? 1231 : 1237);
        }

        public String toString() {
            return "sip: " + this.mAddress;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public EntryLabel getEntryLabel() {
            return EntryLabel.SIP;
        }

        public String getAddress() {
            return this.mAddress;
        }

        public int getType() {
            return this.mType;
        }

        public String getLabel() {
            return this.mLabel;
        }
    }

    public static class AndroidCustomData implements EntryElement {
        private final List<String> mDataList;
        private final String mMimeType;

        public AndroidCustomData(String mimeType, List<String> dataList) {
            this.mMimeType = mimeType;
            this.mDataList = dataList;
        }

        public static AndroidCustomData constructAndroidCustomData(List<String> list) {
            List<String> dataList;
            String mimeType;
            if (list == null) {
                mimeType = null;
                dataList = null;
            } else if (list.size() < 2) {
                mimeType = list.get(0);
                dataList = null;
            } else {
                int max = 16;
                if (list.size() < 16) {
                    max = list.size();
                }
                String mimeType2 = list.get(0);
                dataList = list.subList(1, max);
                mimeType = mimeType2;
            }
            return new AndroidCustomData(mimeType, dataList);
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public void constructInsertOperation(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
            builder.withValueBackReference("raw_contact_id", backReferenceIndex);
            builder.withValue("mimetype", this.mMimeType);
            for (int i = 0; i < this.mDataList.size(); i++) {
                String value = this.mDataList.get(i);
                if (!TextUtils.isEmpty(value)) {
                    builder.withValue("data" + (i + 1), value);
                }
            }
            operationList.add(builder.build());
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public boolean isEmpty() {
            List<String> list;
            return TextUtils.isEmpty(this.mMimeType) || (list = this.mDataList) == null || list.size() == 0;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof AndroidCustomData)) {
                return false;
            }
            AndroidCustomData data = (AndroidCustomData) obj;
            if (!TextUtils.equals(this.mMimeType, data.mMimeType)) {
                return false;
            }
            List<String> list = this.mDataList;
            if (list != null) {
                int size = list.size();
                if (size != data.mDataList.size()) {
                    return false;
                }
                for (int i = 0; i < size; i++) {
                    if (!TextUtils.equals(this.mDataList.get(i), data.mDataList.get(i))) {
                        return false;
                    }
                }
                return true;
            } else if (data.mDataList == null) {
                return true;
            } else {
                return false;
            }
        }

        public int hashCode() {
            String str = this.mMimeType;
            int hash = str != null ? str.hashCode() : 0;
            List<String> list = this.mDataList;
            if (list != null) {
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    String data = it.next();
                    hash = (hash * 31) + (data != null ? data.hashCode() : 0);
                }
            }
            return hash;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("android-custom: " + this.mMimeType + ", data: ");
            List<String> list = this.mDataList;
            builder.append(list == null ? "null" : Arrays.toString(list.toArray()));
            return builder.toString();
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElement
        public EntryLabel getEntryLabel() {
            return EntryLabel.ANDROID_CUSTOM;
        }

        public String getMimeType() {
            return this.mMimeType;
        }

        public List<String> getDataList() {
            return this.mDataList;
        }
    }

    public final void iterateAllData(EntryElementIterator iterator) {
        iterator.onIterationStarted();
        iterator.onElementGroupStarted(this.mNameData.getEntryLabel());
        iterator.onElement(this.mNameData);
        iterator.onElementGroupEnded();
        iterateOneList(this.mPhoneList, iterator);
        iterateOneList(this.mEmailList, iterator);
        iterateOneList(this.mPostalList, iterator);
        iterateOneList(this.mOrganizationList, iterator);
        iterateOneList(this.mImList, iterator);
        iterateOneList(this.mPhotoList, iterator);
        iterateOneList(this.mWebsiteList, iterator);
        iterateOneList(this.mSipList, iterator);
        iterateOneList(this.mNicknameList, iterator);
        iterateOneList(this.mNoteList, iterator);
        iterateOneList(this.mAndroidCustomDataList, iterator);
        BirthdayData birthdayData = this.mBirthday;
        if (birthdayData != null) {
            iterator.onElementGroupStarted(birthdayData.getEntryLabel());
            iterator.onElement(this.mBirthday);
            iterator.onElementGroupEnded();
        }
        AnniversaryData anniversaryData = this.mAnniversary;
        if (anniversaryData != null) {
            iterator.onElementGroupStarted(anniversaryData.getEntryLabel());
            iterator.onElement(this.mAnniversary);
            iterator.onElementGroupEnded();
        }
        iterator.onIterationEnded();
    }

    private void iterateOneList(List<? extends EntryElement> elemList, EntryElementIterator iterator) {
        if (elemList != null && elemList.size() > 0) {
            iterator.onElementGroupStarted(((EntryElement) elemList.get(0)).getEntryLabel());
            for (EntryElement elem : elemList) {
                iterator.onElement(elem);
            }
            iterator.onElementGroupEnded();
        }
    }

    /* access modifiers changed from: private */
    public class IsIgnorableIterator implements EntryElementIterator {
        private boolean mEmpty;

        private IsIgnorableIterator() {
            this.mEmpty = true;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onIterationStarted() {
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onIterationEnded() {
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onElementGroupStarted(EntryLabel label) {
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onElementGroupEnded() {
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public boolean onElement(EntryElement elem) {
            if (elem.isEmpty()) {
                return true;
            }
            this.mEmpty = false;
            return false;
        }

        public boolean getResult() {
            return this.mEmpty;
        }
    }

    private class ToStringIterator implements EntryElementIterator {
        private StringBuilder mBuilder;
        private boolean mFirstElement;

        private ToStringIterator() {
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onIterationStarted() {
            this.mBuilder = new StringBuilder();
            StringBuilder sb = this.mBuilder;
            sb.append("[[hash: " + VCardEntry.this.hashCode() + "\n");
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onElementGroupStarted(EntryLabel label) {
            StringBuilder sb = this.mBuilder;
            sb.append(label.toString() + ": ");
            this.mFirstElement = true;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public boolean onElement(EntryElement elem) {
            if (!this.mFirstElement) {
                this.mBuilder.append(", ");
                this.mFirstElement = false;
            }
            StringBuilder sb = this.mBuilder;
            sb.append("[");
            sb.append(elem.toString());
            sb.append("]");
            return true;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onElementGroupEnded() {
            this.mBuilder.append("\n");
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onIterationEnded() {
            this.mBuilder.append("]]\n");
        }

        public String toString() {
            return this.mBuilder.toString();
        }
    }

    private class InsertOperationConstrutor implements EntryElementIterator {
        private final int mBackReferenceIndex;
        private final List<ContentProviderOperation> mOperationList;

        public InsertOperationConstrutor(List<ContentProviderOperation> operationList, int backReferenceIndex) {
            this.mOperationList = operationList;
            this.mBackReferenceIndex = backReferenceIndex;
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onIterationStarted() {
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onIterationEnded() {
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onElementGroupStarted(EntryLabel label) {
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public void onElementGroupEnded() {
        }

        @Override // com.goodocom.gocsdk.vcard.VCardEntry.EntryElementIterator
        public boolean onElement(EntryElement elem) {
            if (elem.isEmpty()) {
                return true;
            }
            elem.constructInsertOperation(this.mOperationList, this.mBackReferenceIndex);
            return true;
        }
    }

    public String toString() {
        ToStringIterator iterator = new ToStringIterator();
        iterateAllData(iterator);
        return iterator.toString();
    }

    public VCardEntry() {
        this(VCardConfig.VCARD_TYPE_V21_GENERIC);
    }

    public VCardEntry(int vcardType) {
        this(vcardType, null);
    }

    public VCardEntry(int vcardType, Account account) {
        this.mNameData = new NameData();
        this.mVCardType = vcardType;
        this.mAccount = account;
    }

    private void addPhone(int type, String data, String label, boolean isPrimary) {
        String formattedNumber;
        if (this.mPhoneList == null) {
            this.mPhoneList = new ArrayList();
        }
        StringBuilder builder = new StringBuilder();
        String trimmed = data.trim();
        if (type == 6 || VCardConfig.refrainPhoneNumberFormatting(this.mVCardType)) {
            formattedNumber = trimmed;
        } else {
            boolean hasPauseOrWait = false;
            int length = trimmed.length();
            for (int i = 0; i < length; i++) {
                char ch = trimmed.charAt(i);
                if (ch == 'p' || ch == 'P') {
                    builder.append(',');
                    hasPauseOrWait = true;
                } else if (ch == 'w' || ch == 'W') {
                    builder.append(';');
                    hasPauseOrWait = true;
                } else if (PhoneNumberUtils.is12Key(ch) || (i == 0 && ch == '+')) {
                    builder.append(ch);
                }
            }
            if (!hasPauseOrWait) {
                formattedNumber = VCardUtils.PhoneNumberUtilsPort.formatNumber(builder.toString(), VCardUtils.getPhoneNumberFormat(this.mVCardType));
            } else {
                formattedNumber = builder.toString();
            }
        }
        this.mPhoneList.add(new PhoneData(formattedNumber, type, label, isPrimary));
    }

    private void addSip(String sipData, int type, String label, boolean isPrimary) {
        if (this.mSipList == null) {
            this.mSipList = new ArrayList();
        }
        this.mSipList.add(new SipData(sipData, type, label, isPrimary));
    }

    private void addNickName(String nickName) {
        if (this.mNicknameList == null) {
            this.mNicknameList = new ArrayList();
        }
        this.mNicknameList.add(new NicknameData(nickName));
    }

    private void addEmail(int type, String data, String label, boolean isPrimary) {
        if (this.mEmailList == null) {
            this.mEmailList = new ArrayList();
        }
        this.mEmailList.add(new EmailData(data, type, label, isPrimary));
    }

    private void addPostal(int type, List<String> propValueList, String label, boolean isPrimary) {
        if (this.mPostalList == null) {
            this.mPostalList = new ArrayList(0);
        }
        this.mPostalList.add(PostalData.constructPostalData(propValueList, type, label, isPrimary, this.mVCardType));
    }

    private void addNewOrganization(String organizationName, String departmentName, String titleName, String phoneticName, int type, boolean isPrimary) {
        if (this.mOrganizationList == null) {
            this.mOrganizationList = new ArrayList();
        }
        this.mOrganizationList.add(new OrganizationData(organizationName, departmentName, titleName, phoneticName, type, isPrimary));
    }

    private String buildSinglePhoneticNameFromSortAsParam(Map<String, Collection<String>> paramMap) {
        Collection<String> sortAsCollection = paramMap.get(VCardConstants.PARAM_SORT_AS);
        if (sortAsCollection == null || sortAsCollection.size() == 0) {
            return null;
        }
        if (sortAsCollection.size() > 1) {
            Log.w(LOG_TAG, "Incorrect multiple SORT_AS parameters detected: " + Arrays.toString(sortAsCollection.toArray()));
        }
        List<String> sortNames = VCardUtils.constructListFromValue(sortAsCollection.iterator().next(), this.mVCardType);
        StringBuilder builder = new StringBuilder();
        for (String elem : sortNames) {
            builder.append(elem);
        }
        return builder.toString();
    }

    private void handleOrgValue(int type, List<String> orgList, Map<String, Collection<String>> paramMap, boolean isPrimary) {
        String departmentName;
        String organizationName;
        String phoneticName = buildSinglePhoneticNameFromSortAsParam(paramMap);
        if (orgList == null) {
            orgList = sEmptyList;
        }
        int size = orgList.size();
        if (size == 0) {
            organizationName = "";
            departmentName = null;
        } else if (size != 1) {
            String organizationName2 = orgList.get(0);
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < size; i++) {
                if (i > 1) {
                    builder.append(' ');
                }
                builder.append(orgList.get(i));
            }
            organizationName = organizationName2;
            departmentName = builder.toString();
        } else {
            organizationName = orgList.get(0);
            departmentName = null;
        }
        List<OrganizationData> list = this.mOrganizationList;
        if (list == null) {
            addNewOrganization(organizationName, departmentName, null, phoneticName, type, isPrimary);
            return;
        }
        for (OrganizationData organizationData : list) {
            if (organizationData.mOrganizationName == null && organizationData.mDepartmentName == null) {
                organizationData.mOrganizationName = organizationName;
                organizationData.mDepartmentName = departmentName;
                organizationData.mIsPrimary = isPrimary;
                return;
            }
        }
        addNewOrganization(organizationName, departmentName, null, phoneticName, type, isPrimary);
    }

    private void handleTitleValue(String title) {
        List<OrganizationData> list = this.mOrganizationList;
        if (list == null) {
            addNewOrganization(null, null, title, null, 1, false);
            return;
        }
        for (OrganizationData organizationData : list) {
            if (organizationData.mTitle == null) {
                organizationData.mTitle = title;
                return;
            }
        }
        addNewOrganization(null, null, title, null, 1, false);
    }

    private void addIm(int protocol, String customProtocol, String propValue, int type, boolean isPrimary) {
        if (this.mImList == null) {
            this.mImList = new ArrayList();
        }
        this.mImList.add(new ImData(protocol, customProtocol, propValue, type, isPrimary));
    }

    private void addNote(String note) {
        if (this.mNoteList == null) {
            this.mNoteList = new ArrayList(1);
        }
        this.mNoteList.add(new NoteData(note));
    }

    private void addPhotoBytes(String formatName, byte[] photoBytes, boolean isPrimary) {
        if (this.mPhotoList == null) {
            this.mPhotoList = new ArrayList(1);
        }
        this.mPhotoList.add(new PhotoData(formatName, photoBytes, isPrimary));
    }

    private void tryHandleSortAsName(Map<String, Collection<String>> paramMap) {
        Collection<String> sortAsCollection;
        if ((!VCardConfig.isVersion30(this.mVCardType) || (TextUtils.isEmpty(this.mNameData.mPhoneticFamily) && TextUtils.isEmpty(this.mNameData.mPhoneticMiddle) && TextUtils.isEmpty(this.mNameData.mPhoneticGiven))) && (sortAsCollection = paramMap.get(VCardConstants.PARAM_SORT_AS)) != null && sortAsCollection.size() != 0) {
            if (sortAsCollection.size() > 1) {
                Log.w(LOG_TAG, "Incorrect multiple SORT_AS parameters detected: " + Arrays.toString(sortAsCollection.toArray()));
            }
            List<String> sortNames = VCardUtils.constructListFromValue(sortAsCollection.iterator().next(), this.mVCardType);
            int size = sortNames.size();
            if (size > 3) {
                size = 3;
            }
            if (size != 2) {
                if (size == 3) {
                    this.mNameData.mPhoneticMiddle = sortNames.get(2);
                }
                this.mNameData.mPhoneticFamily = sortNames.get(0);
            }
            this.mNameData.mPhoneticGiven = sortNames.get(1);
            this.mNameData.mPhoneticFamily = sortNames.get(0);
        }
    }

    private void handleNProperty(List<String> paramValues, Map<String, Collection<String>> paramMap) {
        tryHandleSortAsName(paramMap);
        if (paramValues != null) {
            int size = paramValues.size();
            int size2 = size;
            if (size >= 1) {
                if (size2 > 5) {
                    size2 = 5;
                }
                if (size2 != 2) {
                    if (size2 != 3) {
                        if (size2 != 4) {
                            if (size2 == 5) {
                                this.mNameData.mSuffix = paramValues.get(4);
                            }
                            this.mNameData.mFamily = paramValues.get(0);
                        }
                        this.mNameData.mPrefix = paramValues.get(3);
                    }
                    this.mNameData.mMiddle = paramValues.get(2);
                }
                this.mNameData.mGiven = paramValues.get(1);
                this.mNameData.mFamily = paramValues.get(0);
            }
        }
    }

    private void handlePhoneticNameFromSound(List<String> elems) {
        if (TextUtils.isEmpty(this.mNameData.mPhoneticFamily) && TextUtils.isEmpty(this.mNameData.mPhoneticMiddle) && TextUtils.isEmpty(this.mNameData.mPhoneticGiven) && elems != null) {
            int size = elems.size();
            int size2 = size;
            if (size >= 1) {
                if (size2 > 3) {
                    size2 = 3;
                }
                if (elems.get(0).length() > 0) {
                    boolean onlyFirstElemIsNonEmpty = true;
                    int i = 1;
                    while (true) {
                        if (i >= size2) {
                            break;
                        } else if (elems.get(i).length() > 0) {
                            onlyFirstElemIsNonEmpty = false;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (onlyFirstElemIsNonEmpty) {
                        String[] namesArray = elems.get(0).split(" ");
                        int nameArrayLength = namesArray.length;
                        if (nameArrayLength == 3) {
                            this.mNameData.mPhoneticFamily = namesArray[0];
                            this.mNameData.mPhoneticMiddle = namesArray[1];
                            this.mNameData.mPhoneticGiven = namesArray[2];
                            return;
                        } else if (nameArrayLength == 2) {
                            this.mNameData.mPhoneticFamily = namesArray[0];
                            this.mNameData.mPhoneticGiven = namesArray[1];
                            return;
                        } else {
                            this.mNameData.mPhoneticGiven = elems.get(0);
                            return;
                        }
                    }
                }
                if (size2 != 2) {
                    if (size2 == 3) {
                        this.mNameData.mPhoneticMiddle = elems.get(2);
                    }
                    this.mNameData.mPhoneticFamily = elems.get(0);
                }
                this.mNameData.mPhoneticGiven = elems.get(1);
                this.mNameData.mPhoneticFamily = elems.get(0);
            }
        }
    }

    public void addProperty(VCardProperty property) {
        boolean isPrimary;
        boolean isPrimary2;
        String label;
        int type;
        boolean isPrimary3;
        Collection<String> typeCollection;
        String label2;
        Collection<String> typeCollection2;
        String propertyName = property.getName();
        Map<String, Collection<String>> paramMap = property.getParameterMap();
        List<String> propertyValueList = property.getValueList();
        byte[] propertyBytes = property.getByteValue();
        if ((propertyValueList != null && propertyValueList.size() != 0) || propertyBytes != null) {
            String propValue = propertyValueList != null ? listToString(propertyValueList).trim() : null;
            if (!propertyName.equals(VCardConstants.PROPERTY_VERSION)) {
                if (propertyName.equals(VCardConstants.PROPERTY_FN)) {
                    this.mNameData.mFormatted = propValue;
                } else if (propertyName.equals(VCardConstants.PROPERTY_NAME)) {
                    if (TextUtils.isEmpty(this.mNameData.mFormatted)) {
                        this.mNameData.mFormatted = propValue;
                    }
                } else if (propertyName.equals(VCardConstants.PROPERTY_N)) {
                    handleNProperty(propertyValueList, paramMap);
                } else if (propertyName.equals(VCardConstants.PROPERTY_SORT_STRING)) {
                    this.mNameData.mSortString = propValue;
                } else if (propertyName.equals(VCardConstants.PROPERTY_NICKNAME) || propertyName.equals(VCardConstants.ImportOnly.PROPERTY_X_NICKNAME)) {
                    addNickName(propValue);
                } else if (propertyName.equals(VCardConstants.PROPERTY_SOUND)) {
                    Collection<String> typeCollection3 = paramMap.get(VCardConstants.PARAM_TYPE);
                    if (typeCollection3 != null && typeCollection3.contains("X-IRMC-N")) {
                        handlePhoneticNameFromSound(VCardUtils.constructListFromValue(propValue, this.mVCardType));
                    }
                } else if (propertyName.equals(VCardConstants.PROPERTY_ADR)) {
                    boolean valuesAreAllEmpty = true;
                    Iterator<String> it = propertyValueList.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (!TextUtils.isEmpty(it.next())) {
                                valuesAreAllEmpty = false;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                    if (!valuesAreAllEmpty) {
                        int type2 = -1;
                        String label3 = null;
                        boolean isPrimary4 = false;
                        Collection<String> typeCollection4 = paramMap.get(VCardConstants.PARAM_TYPE);
                        if (typeCollection4 != null) {
                            for (String typeStringOrg : typeCollection4) {
                                String typeStringUpperCase = typeStringOrg.toUpperCase();
                                if (typeStringUpperCase.equals(VCardConstants.PARAM_TYPE_PREF)) {
                                    isPrimary4 = true;
                                    typeCollection2 = typeCollection4;
                                } else if (typeStringUpperCase.equals(VCardConstants.PARAM_TYPE_HOME)) {
                                    type2 = 1;
                                    label3 = null;
                                    typeCollection2 = typeCollection4;
                                } else {
                                    if (!typeStringUpperCase.equals(VCardConstants.PARAM_TYPE_WORK)) {
                                        typeCollection2 = typeCollection4;
                                        if (!typeStringUpperCase.equalsIgnoreCase(VCardConstants.PARAM_EXTRA_TYPE_COMPANY)) {
                                            if (!typeStringUpperCase.equals(VCardConstants.PARAM_ADR_TYPE_PARCEL) && !typeStringUpperCase.equals(VCardConstants.PARAM_ADR_TYPE_DOM) && !typeStringUpperCase.equals(VCardConstants.PARAM_ADR_TYPE_INTL) && type2 < 0) {
                                                type2 = 0;
                                                if (typeStringUpperCase.startsWith("X-")) {
                                                    label3 = typeStringOrg.substring(2);
                                                } else {
                                                    label3 = typeStringOrg;
                                                }
                                            }
                                        }
                                    } else {
                                        typeCollection2 = typeCollection4;
                                    }
                                    type2 = 2;
                                    label3 = null;
                                }
                                valuesAreAllEmpty = valuesAreAllEmpty;
                                typeCollection4 = typeCollection2;
                            }
                        }
                        if (type2 < 0) {
                            type2 = 1;
                        }
                        addPostal(type2, propertyValueList, label3, isPrimary4);
                    }
                } else if (propertyName.equals(VCardConstants.PROPERTY_EMAIL)) {
                    int type3 = -1;
                    String typeStringUpperCase2 = null;
                    boolean isPrimary5 = false;
                    Collection<String> typeCollection5 = paramMap.get(VCardConstants.PARAM_TYPE);
                    if (typeCollection5 != null) {
                        for (String typeStringOrg2 : typeCollection5) {
                            String typeStringUpperCase3 = typeStringOrg2.toUpperCase();
                            if (typeStringUpperCase3.equals(VCardConstants.PARAM_TYPE_PREF)) {
                                isPrimary5 = true;
                                typeCollection = typeCollection5;
                                typeStringUpperCase2 = typeStringUpperCase2;
                            } else if (typeStringUpperCase3.equals(VCardConstants.PARAM_TYPE_HOME)) {
                                type3 = 1;
                                typeCollection = typeCollection5;
                                typeStringUpperCase2 = typeStringUpperCase2;
                            } else if (typeStringUpperCase3.equals(VCardConstants.PARAM_TYPE_WORK)) {
                                type3 = 2;
                                typeCollection = typeCollection5;
                                typeStringUpperCase2 = typeStringUpperCase2;
                            } else {
                                typeCollection = typeCollection5;
                                if (typeStringUpperCase3.equals(VCardConstants.PARAM_TYPE_CELL)) {
                                    type3 = 4;
                                    typeStringUpperCase2 = typeStringUpperCase2;
                                } else if (type3 < 0) {
                                    if (typeStringUpperCase3.startsWith("X-")) {
                                        label2 = typeStringOrg2.substring(2);
                                    } else {
                                        label2 = typeStringOrg2;
                                    }
                                    type3 = 0;
                                    typeStringUpperCase2 = label2;
                                } else {
                                    typeStringUpperCase2 = typeStringUpperCase2;
                                }
                            }
                            typeCollection5 = typeCollection;
                        }
                    }
                    if (type3 < 0) {
                        type3 = 3;
                    }
                    addEmail(type3, propValue, typeStringUpperCase2, isPrimary5);
                } else if (propertyName.equals(VCardConstants.PROPERTY_ORG)) {
                    boolean isPrimary6 = false;
                    Collection<String> typeCollection6 = paramMap.get(VCardConstants.PARAM_TYPE);
                    if (typeCollection6 != null) {
                        for (String typeString : typeCollection6) {
                            if (typeString.equals(VCardConstants.PARAM_TYPE_PREF)) {
                                isPrimary6 = true;
                            }
                        }
                    }
                    handleOrgValue(1, propertyValueList, paramMap, isPrimary6);
                } else if (propertyName.equals(VCardConstants.PROPERTY_TITLE)) {
                    handleTitleValue(propValue);
                } else if (!propertyName.equals(VCardConstants.PROPERTY_ROLE)) {
                    if (propertyName.equals(VCardConstants.PROPERTY_PHOTO) || propertyName.equals(VCardConstants.PROPERTY_LOGO)) {
                        Collection<String> paramMapValue = paramMap.get(VCardConstants.PARAM_VALUE);
                        if (paramMapValue == null || !paramMapValue.contains(VCardConstants.PROPERTY_URL)) {
                            Collection<String> typeCollection7 = paramMap.get(VCardConstants.PARAM_TYPE);
                            String formatName = null;
                            boolean isPrimary7 = false;
                            if (typeCollection7 != null) {
                                for (String typeValue : typeCollection7) {
                                    if (VCardConstants.PARAM_TYPE_PREF.equals(typeValue)) {
                                        isPrimary7 = true;
                                    } else if (formatName == null) {
                                        formatName = typeValue;
                                    }
                                }
                            }
                            addPhotoBytes(formatName, propertyBytes, isPrimary7);
                        }
                    } else if (propertyName.equals(VCardConstants.PROPERTY_TEL)) {
                        String phoneNumber = null;
                        boolean isSip = false;
                        if (!VCardConfig.isVersion40(this.mVCardType)) {
                            phoneNumber = propValue;
                        } else if (propValue.startsWith("sip:")) {
                            isSip = true;
                        } else if (propValue.startsWith("tel:")) {
                            phoneNumber = propValue.substring(4);
                        } else {
                            phoneNumber = propValue;
                        }
                        if (isSip) {
                            handleSipCase(propValue, paramMap.get(VCardConstants.PARAM_TYPE));
                        } else if (propValue.length() != 0) {
                            Collection<String> typeCollection8 = paramMap.get(VCardConstants.PARAM_TYPE);
                            Object typeObject = VCardUtils.getPhoneTypeFromStrings(typeCollection8, phoneNumber);
                            if (typeObject instanceof Integer) {
                                type = ((Integer) typeObject).intValue();
                                label = null;
                            } else {
                                type = 0;
                                label = typeObject.toString();
                            }
                            if (typeCollection8 == null || !typeCollection8.contains(VCardConstants.PARAM_TYPE_PREF)) {
                                isPrimary3 = false;
                            } else {
                                isPrimary3 = true;
                            }
                            addPhone(type, phoneNumber, label, isPrimary3);
                        }
                    } else if (propertyName.equals(VCardConstants.PROPERTY_X_SKYPE_PSTNNUMBER)) {
                        Collection<String> typeCollection9 = paramMap.get(VCardConstants.PARAM_TYPE);
                        if (typeCollection9 == null || !typeCollection9.contains(VCardConstants.PARAM_TYPE_PREF)) {
                            isPrimary2 = false;
                        } else {
                            isPrimary2 = true;
                        }
                        addPhone(7, propValue, null, isPrimary2);
                    } else if (sImMap.containsKey(propertyName)) {
                        int protocol = sImMap.get(propertyName).intValue();
                        boolean isPrimary8 = false;
                        int type4 = -1;
                        Collection<String> typeCollection10 = paramMap.get(VCardConstants.PARAM_TYPE);
                        if (typeCollection10 != null) {
                            for (String typeString2 : typeCollection10) {
                                if (typeString2.equals(VCardConstants.PARAM_TYPE_PREF)) {
                                    isPrimary8 = true;
                                } else if (type4 < 0) {
                                    if (typeString2.equalsIgnoreCase(VCardConstants.PARAM_TYPE_HOME)) {
                                        type4 = 1;
                                    } else if (typeString2.equalsIgnoreCase(VCardConstants.PARAM_TYPE_WORK)) {
                                        type4 = 2;
                                    }
                                }
                            }
                            isPrimary = isPrimary8;
                        } else {
                            isPrimary = false;
                        }
                        addIm(protocol, null, propValue, type4 < 0 ? 1 : type4, isPrimary);
                    } else if (propertyName.equals(VCardConstants.PROPERTY_NOTE)) {
                        addNote(propValue);
                    } else if (propertyName.equals(VCardConstants.PROPERTY_URL)) {
                        if (this.mWebsiteList == null) {
                            this.mWebsiteList = new ArrayList(1);
                        }
                        this.mWebsiteList.add(new WebsiteData(propValue));
                    } else if (propertyName.equals(VCardConstants.PROPERTY_BDAY)) {
                        this.mBirthday = new BirthdayData(propValue);
                    } else if (propertyName.equals(VCardConstants.PROPERTY_ANNIVERSARY)) {
                        this.mAnniversary = new AnniversaryData(propValue);
                    } else if (propertyName.equals(VCardConstants.PROPERTY_X_PHONETIC_FIRST_NAME)) {
                        this.mNameData.mPhoneticGiven = propValue;
                    } else if (propertyName.equals(VCardConstants.PROPERTY_X_PHONETIC_MIDDLE_NAME)) {
                        this.mNameData.mPhoneticMiddle = propValue;
                    } else if (propertyName.equals(VCardConstants.PROPERTY_X_PHONETIC_LAST_NAME)) {
                        this.mNameData.mPhoneticFamily = propValue;
                    } else if (propertyName.equals(VCardConstants.PROPERTY_IMPP)) {
                        if (propValue.startsWith("sip:")) {
                            handleSipCase(propValue, paramMap.get(VCardConstants.PARAM_TYPE));
                        }
                    } else if (propertyName.equals(VCardConstants.PROPERTY_X_SIP)) {
                        if (!TextUtils.isEmpty(propValue)) {
                            handleSipCase(propValue, paramMap.get(VCardConstants.PARAM_TYPE));
                        }
                    } else if (propertyName.equals(VCardConstants.PROPERTY_X_ANDROID_CUSTOM)) {
                        handleAndroidCustomProperty(VCardUtils.constructListFromValue(propValue, this.mVCardType));
                    } else if (propertyName.toUpperCase().startsWith("X-")) {
                        if (this.mUnknownXData == null) {
                            this.mUnknownXData = new ArrayList();
                        }
                        this.mUnknownXData.add(new Pair<>(propertyName, propValue));
                    }
                }
            }
        }
    }

    private void handleSipCase(String propValue, Collection<String> typeCollection) {
        if (!TextUtils.isEmpty(propValue)) {
            if (propValue.startsWith("sip:")) {
                propValue = propValue.substring(4);
                if (propValue.length() == 0) {
                    return;
                }
            }
            int type = -1;
            String label = null;
            boolean isPrimary = false;
            if (typeCollection != null) {
                for (String typeStringOrg : typeCollection) {
                    String typeStringUpperCase = typeStringOrg.toUpperCase();
                    if (typeStringUpperCase.equals(VCardConstants.PARAM_TYPE_PREF)) {
                        isPrimary = true;
                    } else if (typeStringUpperCase.equals(VCardConstants.PARAM_TYPE_HOME)) {
                        type = 1;
                    } else if (typeStringUpperCase.equals(VCardConstants.PARAM_TYPE_WORK)) {
                        type = 2;
                    } else if (type < 0) {
                        if (typeStringUpperCase.startsWith("X-")) {
                            label = typeStringOrg.substring(2);
                        } else {
                            label = typeStringOrg;
                        }
                        type = 0;
                    }
                }
            }
            if (type < 0) {
                type = 3;
            }
            addSip(propValue, type, label, isPrimary);
        }
    }

    public void addChild(VCardEntry child) {
        if (this.mChildren == null) {
            this.mChildren = new ArrayList();
        }
        this.mChildren.add(child);
    }

    private void handleAndroidCustomProperty(List<String> customPropertyList) {
        if (this.mAndroidCustomDataList == null) {
            this.mAndroidCustomDataList = new ArrayList();
        }
        this.mAndroidCustomDataList.add(AndroidCustomData.constructAndroidCustomData(customPropertyList));
    }

    private String constructDisplayName() {
        String displayName = null;
        if (!TextUtils.isEmpty(this.mNameData.mFormatted)) {
            displayName = this.mNameData.mFormatted;
        } else if (!this.mNameData.emptyStructuredName()) {
            displayName = VCardUtils.constructNameFromElements(this.mVCardType, this.mNameData.mFamily, this.mNameData.mMiddle, this.mNameData.mGiven, this.mNameData.mPrefix, this.mNameData.mSuffix);
        } else if (!this.mNameData.emptyPhoneticStructuredName()) {
            displayName = VCardUtils.constructNameFromElements(this.mVCardType, this.mNameData.mPhoneticFamily, this.mNameData.mPhoneticMiddle, this.mNameData.mPhoneticGiven);
        } else {
            List<EmailData> list = this.mEmailList;
            if (list == null || list.size() <= 0) {
                List<PhoneData> list2 = this.mPhoneList;
                if (list2 == null || list2.size() <= 0) {
                    List<PostalData> list3 = this.mPostalList;
                    if (list3 == null || list3.size() <= 0) {
                        List<OrganizationData> list4 = this.mOrganizationList;
                        if (list4 != null && list4.size() > 0) {
                            displayName = this.mOrganizationList.get(0).getFormattedString();
                        }
                    } else {
                        displayName = this.mPostalList.get(0).getFormattedAddress(this.mVCardType);
                    }
                } else {
                    displayName = this.mPhoneList.get(0).mNumber;
                }
            } else {
                displayName = this.mEmailList.get(0).mAddress;
            }
        }
        if (displayName == null) {
            return "";
        }
        return displayName;
    }

    public void consolidateFields() {
        this.mNameData.displayName = constructDisplayName();
    }

    public boolean isIgnorable() {
        IsIgnorableIterator iterator = new IsIgnorableIterator();
        iterateAllData(iterator);
        return iterator.getResult();
    }

    public ArrayList<ContentProviderOperation> constructInsertOperations(ContentResolver resolver, ArrayList<ContentProviderOperation> operationList) {
        if (operationList == null) {
            operationList = new ArrayList<>();
        }
        if (isIgnorable()) {
            return operationList;
        }
        int backReferenceIndex = operationList.size();
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI);
        Account account = this.mAccount;
        if (account != null) {
            builder.withValue("account_name", account.name);
            builder.withValue("account_type", this.mAccount.type);
        } else {
            builder.withValue("account_name", null);
            builder.withValue("account_type", null);
        }
        operationList.add(builder.build());
        operationList.size();
        iterateAllData(new InsertOperationConstrutor(operationList, backReferenceIndex));
        operationList.size();
        return operationList;
    }

    public static VCardEntry buildFromResolver(ContentResolver resolver) {
        return buildFromResolver(resolver, ContactsContract.Contacts.CONTENT_URI);
    }

    public static VCardEntry buildFromResolver(ContentResolver resolver, Uri uri) {
        return null;
    }

    private String listToString(List<String> list) {
        int size = list.size();
        if (size > 1) {
            StringBuilder builder = new StringBuilder();
            for (String type : list) {
                builder.append(type);
                if (0 < size - 1) {
                    builder.append(";");
                }
            }
            return builder.toString();
        } else if (size == 1) {
            return list.get(0);
        } else {
            return "";
        }
    }

    public final NameData getNameData() {
        return this.mNameData;
    }

    public final List<NicknameData> getNickNameList() {
        return this.mNicknameList;
    }

    public final String getBirthday() {
        BirthdayData birthdayData = this.mBirthday;
        if (birthdayData != null) {
            return birthdayData.mBirthday;
        }
        return null;
    }

    public final List<NoteData> getNotes() {
        return this.mNoteList;
    }

    public final List<PhoneData> getPhoneList() {
        return this.mPhoneList;
    }

    public final List<EmailData> getEmailList() {
        return this.mEmailList;
    }

    public final List<PostalData> getPostalList() {
        return this.mPostalList;
    }

    public final List<OrganizationData> getOrganizationList() {
        return this.mOrganizationList;
    }

    public final List<ImData> getImList() {
        return this.mImList;
    }

    public final List<PhotoData> getPhotoList() {
        return this.mPhotoList;
    }

    public final List<WebsiteData> getWebsiteList() {
        return this.mWebsiteList;
    }

    public final List<VCardEntry> getChildlen() {
        return this.mChildren;
    }

    public String getDisplayName() {
        if (this.mNameData.displayName == null) {
            this.mNameData.displayName = constructDisplayName();
        }
        return this.mNameData.displayName;
    }

    public List<Pair<String, String>> getUnknownXData() {
        return this.mUnknownXData;
    }
}
