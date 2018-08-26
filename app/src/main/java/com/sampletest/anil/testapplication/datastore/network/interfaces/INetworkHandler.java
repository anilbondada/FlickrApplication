package com.sampletest.anil.testapplication.datastore.network.interfaces;

import java.util.HashMap;

/**
 * Created by H211060 on 8/23/2018.
 */

public interface INetworkHandler {
    void getJSONRequest(INetworkParamsDetails details, INetworkResponseListener listener);
    void getImageFromServer(final String id, String url, final IImageDataCallback callback);

}
