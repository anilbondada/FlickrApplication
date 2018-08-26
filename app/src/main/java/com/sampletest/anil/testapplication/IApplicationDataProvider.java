package com.sampletest.anil.testapplication;

import com.sampletest.anil.testapplication.datastore.interfaces.IDataHandler;

/**
 * Created by H211060 on 8/21/2018.
 */

public interface IApplicationDataProvider {

    IDataHandler getDatHandler(IDataHandler.DataHandlerType type);

    String getFlickrAPIKey();

    String getBaseUrl();
}
