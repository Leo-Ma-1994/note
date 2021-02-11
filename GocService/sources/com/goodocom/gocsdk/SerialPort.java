package com.goodocom.gocsdk;

import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {
    private static final String TAG = "SerialPort";
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;

    private static native int newdata(String str);

    private static native FileDescriptor open(String str, int i, int i2);

    public native void close();

    public SerialPort(File device, int baudrate, int flags) throws SecurityException, IOException {
        this.mFd = open(device.getAbsolutePath(), baudrate, flags);
        FileDescriptor fileDescriptor = this.mFd;
        if (fileDescriptor != null) {
            this.mFileInputStream = new FileInputStream(fileDescriptor);
            this.mFileOutputStream = new FileOutputStream(this.mFd);
            return;
        }
        Log.e(TAG, "native open returns null");
        throw new IOException();
    }

    public int InputOutputData() {
        return newdata("goodocom");
    }

    public InputStream getInputStream() {
        return this.mFileInputStream;
    }

    public OutputStream getOutputStream() {
        return this.mFileOutputStream;
    }

    static {
        System.loadLibrary("serial_goc");
    }
}
