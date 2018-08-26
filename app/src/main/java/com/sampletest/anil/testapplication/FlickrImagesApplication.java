package com.sampletest.anil.testapplication;

import android.app.Application;
import android.util.Log;

import com.sampletest.anil.testapplication.datastore.DataStoreFactory;
import com.sampletest.anil.testapplication.datastore.interfaces.IDataHandler;
import com.sampletest.anil.testapplication.utils.Constants;


/**
 * Created by H211060 on 8/21/2018.
 */

public class FlickrImagesApplication extends Application implements IApplicationDataProvider{
    private static final String TAG = "FlickrImagesApplication";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IDataHandler getDatHandler(IDataHandler.DataHandlerType type) {
        return DataStoreFactory.getInstance(this,type);
    }

    @Override
    public String getFlickrAPIKey() {
        return Constants.FLICKR_API_KEY;
    }

    @Override
    public String getBaseUrl() {
        return Constants.BASE_URL+Constants.METHOD_TYPE;
    }
}
