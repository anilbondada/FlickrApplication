package com.sampletest.anil.testapplication.datastore.interfaces;

import android.content.Context;
import android.widget.ImageView;

import com.sampletest.anil.testapplication.datastore.local.LocalImageCache;
import com.sampletest.anil.testapplication.datastore.network.interfaces.IImageDataCallback;
import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkParamsDetails;
import com.sampletest.anil.testapplication.model.FlickerImage;

/**
 * Created by H211060 on 8/21/2018.
 */

public interface IDataHandler {
    void fetchImages(Context context, INetworkParamsDetails networkParamsDetails, IDataFetchResponse callback);

    void updateImageFromFlickr(Context context, ImageView view,FlickerImage imageData,int defaultImageResource);

    void configureCache(LocalImageCache cache);

    enum DataHandlerType{
        DATA_HANDLER_TYPE_NETWORK,
        DATA_HANDLER_TYPE_LOCAL
    }
}
