package com.goodocom.bttek.bt.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class GocPbapContact implements Parcelable {
    public static final int ADDRESS_TYPE_DOM = 1;
    public static final int ADDRESS_TYPE_HOME = 6;
    public static final int ADDRESS_TYPE_INTL = 2;
    public static final int ADDRESS_TYPE_NULL = 0;
    public static final int ADDRESS_TYPE_PARCEL = 4;
    public static final int ADDRESS_TYPE_POSTAL = 3;
    public static final int ADDRESS_TYPE_WORK = 5;
    public static final Parcelable.Creator<GocPbapContact> CREATOR = new Parcelable.Creator<GocPbapContact>() {
        /* class com.goodocom.bttek.bt.aidl.GocPbapContact.AnonymousClass1 */

        @Override // android.os.Parcelable.Creator
        public GocPbapContact createFromParcel(Parcel in) {
            return new GocPbapContact(in.readString(), in.readInt(), in.readString(), in.readString(), in.readString(), in.createIntArray(), in.createStringArray(), in.readInt(), in.createByteArray(), in.createIntArray(), in.createStringArray(), in.createIntArray(), in.createStringArray(), in.readString());
        }

        @Override // android.os.Parcelable.Creator
        public GocPbapContact[] newArray(int size) {
            return new GocPbapContact[size];
        }
    };
    public static final int EMAIL_TYPE_HOME = 5;
    public static final int EMAIL_TYPE_INTERNET = 2;
    public static final int EMAIL_TYPE_NULL = 0;
    public static final int EMAIL_TYPE_PREF = 1;
    public static final int EMAIL_TYPE_WORK = 4;
    public static final int EMAIL_TYPE_X400 = 3;
    public static final int NUMBER_TYPE_CELL = 7;
    public static final int NUMBER_TYPE_FAX = 5;
    public static final int NUMBER_TYPE_HOME = 3;
    public static final int NUMBER_TYPE_MSG = 6;
    public static final int NUMBER_TYPE_NULL = 0;
    public static final int NUMBER_TYPE_PAGER = 8;
    public static final int NUMBER_TYPE_PREF = 1;
    public static final int NUMBER_TYPE_VOICE = 4;
    public static final int NUMBER_TYPE_WORK = 2;
    public static final int STORAGE_TYPE_CALL_LOGS = 8;
    public static final int STORAGE_TYPE_DIALED_CALLS = 7;
    public static final int STORAGE_TYPE_FAVORITE = 4;
    public static final int STORAGE_TYPE_MISSED_CALLS = 5;
    public static final int STORAGE_TYPE_PHONE_MEMORY = 2;
    public static final int STORAGE_TYPE_RECEIVED_CALLS = 6;
    public static final int STORAGE_TYPE_SIM = 1;
    public static final int STORAGE_TYPE_SPEEDDIAL = 3;
    private final String[] mAddress;
    private final int[] mAddressType;
    private final String[] mEmail;
    private final int[] mEmailType;
    private final String mFirstName;
    private final String mLastName;
    private final String mMiddleName;
    private final String[] mNumber;
    private final String mOrg;
    private final int[] mPhoneType;
    private final byte[] mPhoto;
    private final int mPhotoType;
    private final String mRemoteAddress;
    private final int mStorageType;

    public GocPbapContact(String bt_address, int storage, String firstName, String middleName, String lastName, int[] phoneType, String[] number, int photoType, byte[] photo, int[] emailType, String[] email, int[] addressType, String[] address, String org2) {
        this.mRemoteAddress = bt_address;
        this.mStorageType = storage;
        this.mFirstName = firstName;
        this.mMiddleName = middleName;
        this.mLastName = lastName;
        this.mPhoneType = phoneType;
        this.mNumber = number;
        this.mPhotoType = photoType;
        this.mPhoto = (photo == null || photo.length <= 0) ? null : photo;
        this.mEmailType = emailType;
        this.mEmail = email;
        this.mAddressType = addressType;
        this.mAddress = address;
        this.mOrg = org2;
    }

    public String getRemoteAddress() {
        return this.mRemoteAddress;
    }

    public int getStorageType() {
        return this.mStorageType;
    }

    public String getFirstName() {
        return this.mFirstName;
    }

    public String getMiddleName() {
        return this.mMiddleName;
    }

    public String getLastName() {
        return this.mLastName;
    }

    public int[] getPhoneTypeArray() {
        return this.mPhoneType;
    }

    public String[] getNumberArray() {
        return this.mNumber;
    }

    public int getPhotoType() {
        return this.mPhotoType;
    }

    public byte[] getPhotoByteArray() {
        return this.mPhoto;
    }

    public int[] getEmailTypeArray() {
        return this.mEmailType;
    }

    public String[] getEmailArray() {
        return this.mEmail;
    }

    public int[] getAddressType() {
        return this.mAddressType;
    }

    public String[] getAddressArray() {
        return this.mAddress;
    }

    public String getOrg() {
        return this.mOrg;
    }

    @Override // java.lang.Object
    public String toString() {
        int under_line;
        StringBuilder builder = new StringBuilder("===NfPbapContact ");
        int i = this.mStorageType;
        switch (i) {
            case 1:
                builder.append("Sim");
                break;
            case 2:
                builder.append("Memory");
                break;
            case 3:
                builder.append("Speed Dial");
                break;
            case 4:
                builder.append("Favorite");
                break;
            case 5:
                builder.append("Missed Calls");
                break;
            case 6:
                builder.append("Received Calls");
                break;
            case 7:
                builder.append("Dialed Calls");
                break;
            case 8:
                builder.append("Combine Calllogs");
                break;
            default:
                builder.append(i);
                break;
        }
        builder.append("===\n   RemoteAddress: ");
        builder.append(this.mRemoteAddress + "\n");
        builder.append("   Name: " + this.mFirstName + "|" + this.mMiddleName + "|" + this.mLastName + "\n");
        if (this.mNumber != null) {
            for (int under_line2 = 0; under_line2 < this.mNumber.length; under_line2++) {
                builder.append("   Number: (" + getNumberTypeString(this.mPhoneType[under_line2]) + ") " + this.mNumber[under_line2] + "\n");
            }
        }
        byte[] bArr = this.mPhoto;
        if (bArr == null || bArr.length <= 0) {
            builder.append("   Photo: no\n");
        } else {
            builder.append("   Photo: yes\n");
        }
        if (this.mEmail != null) {
            for (int under_line3 = 0; under_line3 < this.mEmail.length; under_line3++) {
                builder.append("   e-mail: (" + getEmailTypeString(this.mEmailType[under_line3]) + ") " + this.mEmail[under_line3] + "\n");
            }
        }
        if (this.mAddress != null) {
            for (int under_line4 = 0; under_line4 < this.mAddress.length; under_line4++) {
                builder.append("   Address: (" + getAddressTypeString(this.mAddressType[under_line4]) + ") " + this.mAddress[under_line4] + "\n");
            }
        }
        if (this.mOrg != null) {
            builder.append("   Org: " + this.mOrg + "\n");
        }
        switch (this.mStorageType) {
            case 1:
                under_line = 20 + 3;
                break;
            case 2:
                under_line = 20 + 6;
                break;
            case 3:
                under_line = 20 + 10;
                break;
            case 4:
                under_line = 20 + 8;
                break;
            case 5:
                under_line = 20 + 12;
                break;
            case 6:
                under_line = 20 + 14;
                break;
            case 7:
                under_line = 20 + 12;
                break;
            case 8:
                under_line = 20 + 16;
                break;
            default:
                under_line = 20 + 1;
                break;
        }
        for (int i2 = 0; i2 < under_line; i2++) {
            builder.append("=");
        }
        return builder.toString();
    }

    private String getEmailTypeString(int type) {
        if (type == 0) {
            return "Null";
        }
        if (type == 1) {
            return "Pref";
        }
        if (type == 2) {
            return "Internet";
        }
        if (type == 3) {
            return "X400";
        }
        if (type == 4) {
            return "Work";
        }
        if (type != 5) {
            return "Unknown";
        }
        return "Home";
    }

    private String getAddressTypeString(int type) {
        switch (type) {
            case 0:
                return "Null";
            case 1:
                return "Pref";
            case 2:
                return "International";
            case 3:
                return "Postal";
            case 4:
                return "Parcel";
            case 5:
                return "Work";
            case 6:
                return "Home";
            default:
                return "Unknown";
        }
    }

    private String getNumberTypeString(int type) {
        switch (type) {
            case 0:
                return "Null";
            case 1:
                return "Pref";
            case 2:
                return "Work";
            case 3:
                return "Home";
            case 4:
                return "Voice";
            case 5:
                return "Fax";
            case 6:
                return "Msg";
            case 7:
                return "Cell";
            case 8:
                return "Pager";
            default:
                return "Unknown";
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mRemoteAddress);
        out.writeInt(this.mStorageType);
        out.writeString(this.mFirstName);
        out.writeString(this.mMiddleName);
        out.writeString(this.mLastName);
        out.writeIntArray(this.mPhoneType);
        out.writeStringArray(this.mNumber);
        out.writeInt(this.mPhotoType);
        byte[] bArr = this.mPhoto;
        if (bArr == null) {
            bArr = new byte[0];
        }
        out.writeByteArray(bArr);
        out.writeIntArray(this.mEmailType);
        out.writeStringArray(this.mEmail);
        out.writeIntArray(this.mAddressType);
        out.writeStringArray(this.mAddress);
        out.writeString(this.mOrg);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }
}
