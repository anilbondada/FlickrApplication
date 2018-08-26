package com.sampletest.anil.testapplication.datastore.network.interfaces;

/**
 * Created by H211060 on 8/23/2018.
 */

public interface INetworkResponseListener {
    void onNetworkResponse(boolean status,int statusCode,String msg);
}
