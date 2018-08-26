package com.sampletest.anil.testapplication.datastore;

import android.content.Context;

import com.sampletest.anil.testapplication.datastore.interfaces.IDataHandler;
import com.sampletest.anil.testapplication.datastore.local.LocalDataHandler;
import com.sampletest.anil.testapplication.datastore.network.NetworkDataHandler;

/**
 * Created by H211060 on 8/21/2018.
 */

public class DataStoreFactory {

    private static IDataHandler localDataHandler;
    private static IDataHandler networkDataHandler;

    public static IDataHandler getInstance(Context context,IDataHandler.DataHandlerType handlerType){
        IDataHandler dataHandler = null;
        switch (handlerType){
            case DATA_HANDLER_TYPE_LOCAL:
            {
                if(localDataHandler == null){
                    localDataHandler = new LocalDataHandler(context);
                }
                dataHandler = localDataHandler;
                break;
            }
            case DATA_HANDLER_TYPE_NETWORK:
            {
                if(networkDataHandler == null){
                    networkDataHandler = new NetworkDataHandler();
                }
                dataHandler = networkDataHandler;
                break;
            }
        }
        return dataHandler;
    }

}
