package com.goodocom.bttek.util.normal;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import com.goodocom.bttek.BuildConfig;
import com.goodocom.bttek.bt.res.NfDef;
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
import java.util.Set;

public final class NfUtils {
    static final int BD_ADDR_LEN = 6;
    static final String TAG = "NfUtils";
    static int decodeCount = 0;

    public static boolean isRunningForeground(Context context, String packageName) {
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (appProcessInfo.importance == 100 && appProcessInfo.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

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
            Log.i(TAG, "        Package Name : " + pn);
            Log.i(TAG, "        Version Code : " + vc);
            Log.i(TAG, "        Version Name : " + vn);
            Log.i(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            return vn;
        }
        Log.e(TAG, "In onCreate() : mPackageInfo is null");
        return BuildConfig.FLAVOR;
    }

    private void dumpBtConfig() {
        String content = BuildConfig.FLAVOR;
        try {
            content = getStringFromFile("/data/misc/bluedroid/bt_config.xml");
            Log.e(TAG, "Piggy Check bt_config.xml dump:\n|" + content + "|");
        } catch (Exception var4) {
            var4.printStackTrace();
        }
        parseAddressContent(content, "90:b9:31:14:e3:da");
    }

    public static String[] setToString(Set<String> allSet) {
        String[] temp = (String[]) allSet.toArray(new String[0]);
        String[] intArray = new String[temp.length];
        for (int i = 0; i < temp.length; i++) {
            intArray[i] = temp[i];
        }
        return intArray;
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
            NfLog.e(TAG, "File(" + path + ") delete fail.");
            return false;
        }
        NfLog.e(TAG, "File(" + path + ") not exists.");
        return false;
    }

    public static String createPhotoFile(String name, byte[] photo) {
        if (photo == null || photo.length <= 0) {
            return BuildConfig.FLAVOR;
        }
        File outDir = new File(NfDef.PBAP_PHOTO_FOLDER);
        if (!outDir.isDirectory()) {
            outDir.mkdir();
        }
        if (!outDir.isDirectory()) {
            Log.e(TAG, "Piggy Check can't create folder !!");
            return BuildConfig.FLAVOR;
        }
        File f = new File(outDir, name + ".jpeg");
        Log.e(TAG, "Piggy Check Before check readable/writeable");
        f.setReadable(true);
        Log.e(TAG, "Piggy Check After set readable");
        f.setWritable(true);
        Log.e(TAG, "Piggy Check After set writeable");
        try {
            f.createNewFile();
            Log.e(TAG, "Piggy Check File Name : " + f.getName());
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
            NfLog.d(TAG, "processPhoto(): " + photoPath);
            File file = new File(photoPath);
            int size = (int) file.length();
            byte[] bytes = new byte[size];
            NfLog.e(TAG, "Piggy Check file.length: " + size);
            try {
                BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
                buf.read(bytes, 0, bytes.length);
                buf.close();
                file.delete();
                NfLog.e(TAG, "Piggy Check bytes.length: " + bytes.length);
                String photoString = new String(bytes);
                NfLog.e(TAG, "Piggy Check photoString : " + photoString);
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
                NfLog.d(TAG, "base64_decodeArray length is " + base64_decodeArray.length);
            } else {
                NfLog.d(TAG, "base64_decodeArray is null.");
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
            NfLog.e(TAG, "Path: " + path + " is not folder.");
            return false;
        } else if (f.canWrite()) {
            return true;
        } else {
            NfLog.e(TAG, "Path: " + path + " can't write.");
            return false;
        }
    }
}
