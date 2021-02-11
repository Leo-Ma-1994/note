package com.goodocom.bttek.util.normal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import com.goodocom.bttek.bt.res.GocDef;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public final class GocUtils {
    static final int BD_ADDR_LEN = 6;
    static final String TAG = GocUtils.class.getSimpleName();
    static int decodeCount = 0;

    public static byte[] getBytesFromAddress(String address) {
        int j = 0;
        byte[] output = new byte[6];
        int i = 0;
        while (i < address.length()) {
            if (address.charAt(i) != ':') {
                output[j] = (byte) Integer.parseInt(address.substring(i, i + 2), 16);
                j++;
                i++;
            }
            i++;
        }
        return output;
    }

    public static String getAddressStringFromByte(byte[] address) {
        if (address == null || address.length != 6) {
            return null;
        }
        return String.format("%02X:%02X:%02X:%02X:%02X:%02X", Byte.valueOf(address[0]), Byte.valueOf(address[1]), Byte.valueOf(address[2]), Byte.valueOf(address[3]), Byte.valueOf(address[4]), Byte.valueOf(address[5]));
    }

    public static String printAppVersion(Context c) {
        PackageInfo pi = null;
        try {
            pi = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Exception when getting package information !!!");
        }
        if (pi != null) {
            String pn = pi.packageName;
            int vc = pi.versionCode;
            String vn = pi.versionName;
            Log.i(TAG, "+++++++++++++++++++++++++ nFore +++++++++++++++++++++++++");
            String str = TAG;
            Log.i(str, "        Package Name : " + pn);
            String str2 = TAG;
            Log.i(str2, "        Version Code : " + vc);
            String str3 = TAG;
            Log.i(str3, "        Version Name : " + vn);
            Log.i(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return vn;
        }
        Log.e(TAG, "In onCreate() : mPackageInfo is null");
        return "";
    }

    private void dumpBtConfig() {
        String content = "";
        try {
            content = getStringFromFile("/data/misc/bluedroid/bt_config.xml");
            String str = TAG;
            Log.e(str, "Piggy Check bt_config.xml dump:\n|" + content + "|");
        } catch (Exception var4) {
            var4.printStackTrace();
        }
        parseAddressContent(content, "90:b9:31:14:e3:da");
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = reader.readLine();
            if (line != null) {
                sb.append(line);
                sb.append("\n");
            } else {
                reader.close();
                return sb.toString();
            }
        }
    }

    public static String getStringFromFile(String filePath) throws Exception {
        FileInputStream fin = new FileInputStream(new File(filePath));
        String ret = convertStreamToString(fin);
        fin.close();
        return ret;
    }

    private String parseAddressContent(String content, String address) {
        String address2 = address.toLowerCase();
        int end = content.indexOf(address2);
        Log.e(TAG, "Piggy Check start: " + end);
        String temp = content.substring(end + -10, end);
        Log.e(TAG, "Piggy Check temp: |" + temp + "|");
        int start = temp.indexOf("<");
        int end2 = temp.indexOf(" ");
        Log.e(TAG, "Piggy Check start: " + start);
        Log.e(TAG, "Piggy Check end: " + end2);
        if (end2 < start) {
            temp = temp.substring(start);
            Log.e(TAG, "Piggy Check temp: |" + temp + "|");
        }
        String addressHeader = temp.substring(temp.indexOf("<") + 1, temp.indexOf(" "));
        Log.e(TAG, "Piggy Check addressHeader: |" + addressHeader + "|");
        String dataEnd = "</" + addressHeader + ">";
        int start2 = content.indexOf("<" + addressHeader + " Tag=\"" + address2 + "\">");
        int end3 = content.indexOf(dataEnd, start2);
        Log.e(TAG, "Piggy Check start: " + start2);
        Log.e(TAG, "Piggy Check end: " + end3);
        String result = content.substring(start2, dataEnd.length() + end3);
        if (result.indexOf("AvrcpVersion") != -1) {
            return result;
        }
        int end4 = content.indexOf(dataEnd, end3 + 5);
        Log.e(TAG, "Piggy Check start: " + start2);
        Log.e(TAG, "Piggy Check end: " + end4);
        String result2 = content.substring(start2, dataEnd.length() + end4);
        Log.e(TAG, "Piggy Check result: |" + result2 + "|");
        return result2;
    }

    public static boolean deleteFile(String path) {
        File f = new File(path);
        if (f.exists()) {
            f.delete();
            if (!f.exists()) {
                return true;
            }
            String str = TAG;
            Log.e(str, "File(" + path + ") delete fail.");
            return false;
        }
        String str2 = TAG;
        Log.e(str2, "File(" + path + ") not exists.");
        return false;
    }

    public static String createPhotoFile(String name, byte[] photo) {
        if (photo == null || photo.length <= 0) {
            return "";
        }
        File outDir = new File(GocDef.PBAP_PHOTO_FOLDER);
        if (!outDir.isDirectory()) {
            outDir.mkdir();
        }
        if (!outDir.isDirectory()) {
            Log.e(TAG, "Piggy Check can't create folder !!");
            return "";
        }
        File f = new File(outDir, name + ".jpeg");
        Log.e(TAG, "Piggy Check Before check readable/writeable");
        f.setReadable(true);
        Log.e(TAG, "Piggy Check After set readable");
        f.setWritable(true);
        Log.e(TAG, "Piggy Check After set writeable");
        try {
            f.createNewFile();
            String str = TAG;
            Log.e(str, "Piggy Check File Name : " + f.getName());
        } catch (IOException var6) {
            var6.printStackTrace();
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
            bos.write(photo);
            bos.flush();
            bos.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }
        return f.getPath();
    }

    public static byte[] processPhoto(byte[] path) throws IllegalArgumentException {
        try {
            String photoPath = new String(path, "UTF-8");
            String str = TAG;
            Log.d(str, "processPhoto(): " + photoPath);
            File file = new File(photoPath);
            int size = (int) file.length();
            byte[] bytes = new byte[size];
            String str2 = TAG;
            Log.e(str2, "Piggy Check file.length: " + size);
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();
                file.delete();
                String str3 = TAG;
                Log.e(str3, "Piggy Check bytes.length: " + bytes.length);
                String photoString = new String(bytes);
                String str4 = TAG;
                Log.e(str4, "Piggy Check photoString : " + photoString);
                return decodePhoto(bytes);
            } catch (FileNotFoundException var6) {
                var6.printStackTrace();
                return null;
            } catch (IOException var7) {
                var7.printStackTrace();
                return null;
            }
        } catch (UnsupportedEncodingException var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public static byte[] decodePhoto(byte[] photo) throws IllegalArgumentException {
        decodeCount++;
        Log.e(TAG, "Piggy Check decodePhoto() start. count: " + decodeCount);
        try {
            byte[] base64_decodeArray = Base64.decode(photo, 0);
            if (base64_decodeArray != null) {
                Log.d(TAG, "base64_decodeArray length is " + base64_decodeArray.length);
            } else {
                Log.d(TAG, "base64_decodeArray is null.");
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(base64_decodeArray, 0, base64_decodeArray.length);
            if (bitmap == null) {
                decodeCount--;
                Log.d(TAG, "Piggy Check decodePhoto() bitmap is null. count: " + decodeCount);
                return base64_decodeArray;
            }
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, blob);
            decodeCount--;
            return blob.toByteArray();
        } catch (IllegalArgumentException var5) {
            decodeCount--;
            throw var5;
        }
    }

    public static boolean isFilePathValid(String path) {
        File f = new File(Environment.getExternalStorageDirectory().getPath() + path);
        if (!f.isDirectory()) {
            String str = TAG;
            Log.e(str, "Path: " + path + " is not folder.");
            return false;
        } else if (f.canWrite()) {
            return true;
        } else {
            String str2 = TAG;
            Log.e(str2, "Path: " + path + " can't write.");
            return false;
        }
    }
}
