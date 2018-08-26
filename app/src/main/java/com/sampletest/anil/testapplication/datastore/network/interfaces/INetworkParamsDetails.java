package com.sampletest.anil.testapplication.datastore.network.interfaces;

import java.util.HashMap;

/**
 * Created by H211060 on 8/23/2018.
 */

public interface INetworkParamsDetails {
    HashMap<String,String> getHeaders();
    HashMap<String,String> getParams();
    String getUrl();
}
