package com.sampletest.anil.testapplication.datastore.local;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;

import com.sampletest.anil.testapplication.R;
import com.sampletest.anil.testapplication.datastore.DataStoreFactory;
import com.sampletest.anil.testapplication.datastore.interfaces.IDataFetchResponse;
import com.sampletest.anil.testapplication.datastore.interfaces.IDataHandler;
import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkParamsDetails;
import com.sampletest.anil.testapplication.model.FlickerImage;
import com.sampletest.anil.testapplication.utils.Utils;


/**
 * Created by H211060 on 8/21/2018.
 */

public class LocalDataHandler implements IDataHandler {
    private LocalImageCache cache;
    private IDataHandler networkDataHandler;
    public LocalDataHandler(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE);
        int maxKb = am.getMemoryClass() * 1024;
        int limitKb = maxKb / 8;
        cache = new LocalImageCache(limitKb);
        networkDataHandler = DataStoreFactory.getInstance(context,DataHandlerType.DATA_HANDLER_TYPE_NETWORK);
        networkDataHandler.configureCache(cache);
    }


    @Override
    public void fetchImages(final Context context, INetworkParamsDetails networkParamsDetails, final IDataFetchResponse callback) {
        boolean networkAvailable = Utils.isDeviceOnline(context);
        if(networkAvailable) {
            networkDataHandler.fetchImages(context, networkParamsDetails, callback);
        }else{
            callback.onFailedResponse(context.getResources().getString(R.string.no_internet_connection));
/*
            //get it locally from the device
            DBInteractionTask dbInteractionTask = new DBInteractionTask(context, null, new IFlickrImageDataResponse() {
                @Override
                public void onFlickerImagesFecthed(List<Photo> flickrImages) {
                    final List<FlickerImage> imageData = new ArrayList<>(flickrImages.size());
                    for(final Photo photo:flickrImages){
                        String imageUrl = Utils.getImageUrl(photo);
                        if(Utils.isFileExists(context,photo.getId())) {
                            imageData.add(new FlickerImage(photo.getId(), imageUrl, photo.getTitle()));
                        }
                    }
                    callback.onSuccessResponse(imageData);
                }
            });
            Utils.doTheTaskInSerial(dbInteractionTask,DB_OPERATION_READ);
*/
        }
    }

    @Override
    public void updateImageFromFlickr(Context context, ImageView view, FlickerImage imageData, int defaultImageResource) {
        view.setImageResource(defaultImageResource);
        Bitmap image = cache.get(imageData.getUrl());
        if (image != null) {
            view.setImageBitmap(image);
        }
        else{
            networkDataHandler.updateImageFromFlickr(context,view,imageData,defaultImageResource);
        }
    }

    @Override
    public void configureCache(LocalImageCache cache) {
        this.cache = cache;
    }
}
