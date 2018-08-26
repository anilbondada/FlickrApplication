package com.sampletest.anil.testapplication.datastore.network.interfaces;

import android.graphics.Bitmap;

/**
 * Created by H211060 on 8/24/2018.
 */

public interface IImageDataCallback {
    void onImageFetchedSuccess(String id,Bitmap bitmap);
    void onImageFetchedFailed(String error);
}
