package com.sampletest.anil.testapplication.datastore.network;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.sampletest.anil.testapplication.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ComponentCallbacks2.TRIM_MEMORY_BACKGROUND;
import static android.content.ComponentCallbacks2.TRIM_MEMORY_MODERATE;

/**
 * Created by H211060 on 8/26/2018.
 */

public class ImageDownloaderWithLRU implements ComponentCallbacks2 {
    private TCLruCache cache;
    private static ImageDownloaderWithLRU mInstance;
    private ImageDownloaderWithLRU(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        int maxKb = am.getMemoryClass() * 1024;
        int limitKb = maxKb / 8; // 1/8th of total ram
        cache = new TCLruCache(limitKb);
    }

    public static ImageDownloaderWithLRU getInstance(Context context) {
        if(mInstance == null) {
            mInstance = new ImageDownloaderWithLRU(context);
        }
        return mInstance;
    }

    public void display(String url, ImageView imageview, int defaultresource) {
        imageview.setImageResource(defaultresource);
        Bitmap image = cache.get(url);
        if (image != null) {
            imageview.setImageBitmap(image);
        }
        else {
            SetImageTask imageTask = new SetImageTask(imageview);
            Utils.doTheTaskInParallel(imageTask,url);
        }
    }

    private class TCLruCache extends LruCache<String, Bitmap> {

        public TCLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            int kbOfBitmap = value.getByteCount() / 1024;
            return kbOfBitmap;
        }
    }

    private class SetImageTask extends AsyncTask<Object, Void, Integer> {
        private ImageView imageview;
        private Bitmap bmp;

        public SetImageTask(ImageView imageview) {
            this.imageview = imageview;
        }

        @Override
        protected Integer doInBackground(Object... params) {
            String url = (String )params[0];
            try {
                bmp = getBitmapFromURL(url);
                if (bmp != null) {
                    cache.put(url, bmp);
                }
                else {
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result == 1) {
                imageview.setImageBitmap(bmp);
            }
            super.onPostExecute(result);
        }

        private Bitmap getBitmapFromURL(String src) {
            try {
                URL url = new URL(src);
                HttpURLConnection connection
                        = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    }

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onTrimMemory(int level) {
        if (level >= TRIM_MEMORY_MODERATE) {
            cache.evictAll();
        }
        else if (level >= TRIM_MEMORY_BACKGROUND) {
            cache.trimToSize(cache.size() / 2);
        }
    }
}


