package com.goodocom.gocsdk.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtils {
    public static final String PATH = "/data/goc/";
    private static String mUrl;
    private static ImageView showImageView;

    public static String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public static void setImageBitmap(String url, ImageView iv) {
        showImageView = iv;
        mUrl = url;
        Bitmap loacalBitmap = getLoacalBitmap(Environment.getExternalStorageDirectory() + PATH + getFileName(url));
        if (loacalBitmap != null) {
            showImageView.setImageBitmap(loacalBitmap);
            Log.d("ImageUtils", "本地获取");
            return;
        }
        Log.d("ImageUtils", "网络获取");
        new DownImgAsyncTask().execute(url);
    }

    public static Bitmap getBitmapByUrl(String url) {
        IOException e;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            Log.d("fjasmin 图片", "1");
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                    e = e2;
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e4) {
                    e = e4;
                }
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e5) {
                    e5.printStackTrace();
                }
            }
            throw th;
        }
        return bitmap;
        e.printStackTrace();
        return bitmap;
    }

    public static File saveImage(Bitmap bmp, String path, String fileName) {
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return file;
    }

    public static Bitmap getLoacalBitmap(String url) {
        if (url == null) {
            return null;
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(url);
            Bitmap decodeStream = BitmapFactory.decodeStream(fis);
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return decodeStream;
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e3) {
                    e3.printStackTrace();
                }
            }
            return null;
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    static class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {
        DownImgAsyncTask() {
        }

        /* access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(String... params) {
            return ImageUtils.getBitmapByUrl(params[0]);
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap result) {
            super.onPostExecute((DownImgAsyncTask) result);
            if (result != null) {
                Log.d("ImageUtils", ImageUtils.saveImage(result, Environment.getExternalStorageDirectory() + ImageUtils.PATH, ImageUtils.getFileName(ImageUtils.mUrl)).toString());
                ImageUtils.showImageView.setImageBitmap(result);
            }
        }
    }
}
