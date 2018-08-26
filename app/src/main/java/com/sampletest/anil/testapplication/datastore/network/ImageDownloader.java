package com.sampletest.anil.testapplication.datastore.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.sampletest.anil.testapplication.datastore.network.interfaces.IImageDataCallback;
import com.sampletest.anil.testapplication.model.FlickerImage;
import com.sampletest.anil.testapplication.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by H211060 on 8/24/2018.
 */

public class ImageDownloader extends AsyncTask<Object,Void,Bitmap>{
    private static final String TAG = "ImageDownloader";
    IImageDataCallback callback;
    String id;
    Context mContext;
    public ImageDownloader(Context context,String id, IImageDataCallback callback){
        this.mContext = context;
        this.id = id;
        this.callback = callback;
    }


    private Bitmap getBitmapFromURL(String src) {
        Bitmap myBitmap = null;
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (Exception e) {
            Log.d(TAG, "getBitmapFromURL: exception: "+e.getMessage() );
        }
        return myBitmap;
    }

    @Override
    protected Bitmap doInBackground(Object ... images) {
        final FlickerImage imageData = (FlickerImage)images[0];
        Bitmap bm = null;
        if(imageData != null){
            bm = getBitmapFromURL(imageData.getUrl());
        }
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        callback.onImageFetchedSuccess(id,bitmap);
    }
}
