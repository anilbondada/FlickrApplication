package com.sampletest.anil.testapplication.datastore.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sampletest.anil.testapplication.R;
import com.sampletest.anil.testapplication.datastore.interfaces.IDataFetchResponse;
import com.sampletest.anil.testapplication.datastore.interfaces.IDataHandler;
import com.sampletest.anil.testapplication.datastore.local.LocalImageCache;
import com.sampletest.anil.testapplication.datastore.network.interfaces.IImageDataCallback;
import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkParamsDetails;
import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkResponseListener;
import com.sampletest.anil.testapplication.model.FlickerImage;
import com.sampletest.anil.testapplication.model.ImageDownloadedData;
import com.sampletest.anil.testapplication.model.Photo;
import com.sampletest.anil.testapplication.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by H211060 on 8/21/2018.
 */

public class NetworkDataHandler implements IDataHandler{
    private static final String TAG = "NetworkDataHandler";
    private LocalImageCache mCache;

    @Override
    public void fetchImages(final Context context, INetworkParamsDetails details, final IDataFetchResponse callback) {
        DataDownloader dataDownloader = new DataDownloader(details, new INetworkResponseListener() {
            @Override
            public void onNetworkResponse(boolean status, int statusCode, String msg) {
                if(status){
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    ImageDownloadedData data =  gson.fromJson(msg,ImageDownloadedData.class);
                    Utils.setCurrentAndTotalPages(data.getPhotos().getPage(),data.getPhotos().getPages());
                    final List<Photo> photos = data.getPhotos().getPhoto();
                    final List<FlickerImage> imageData = new ArrayList<>(photos.size());
                    for(final Photo photo:photos){
                        String imageUrl = Utils.getImageUrl(photo);
                        imageData.add(new FlickerImage(photo.getId(),imageUrl,photo.getTitle()));
                    }
                    if(imageData != null && imageData.size() > 0)
                        callback.onSuccessResponse(imageData);
                    else
                        callback.onFailedResponse(context.getResources().getString(R.string.no_results_found));
                }
                else{
                    callback.onFailedResponse(msg);
                }
            }
        });
        Utils.doTheTaskInSerial(dataDownloader,"");
    }

    @Override
    public void updateImageFromFlickr(Context context, final ImageView view, final FlickerImage imageData, final int defaultImageResource) {
        ImageDownloader imageDownloader = new ImageDownloader(context, imageData.getId(), new IImageDataCallback() {
            @Override
            public void onImageFetchedSuccess(String id, Bitmap bitmap) {
                view.setImageBitmap(bitmap);
                if(mCache != null){
                    if(bitmap != null && imageData.getUrl() != null)
                        mCache.put(imageData.getUrl(),bitmap);
                }
            }

            @Override
            public void onImageFetchedFailed(String error) {
                view.setImageResource(defaultImageResource);
            }
        });
        Utils.doTheTaskInParallel(imageDownloader,imageData);
    }

    @Override
    public void configureCache(LocalImageCache cache) {
        this.mCache = cache;
    }
}
