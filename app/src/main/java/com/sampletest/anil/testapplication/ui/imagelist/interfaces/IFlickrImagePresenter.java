package com.sampletest.anil.testapplication.ui.imagelist.interfaces;

import com.sampletest.anil.testapplication.datastore.interfaces.IDataHandler;
import com.sampletest.anil.testapplication.ui.base.interfaces.IBasePresenter;

/**
 * Created by H211060 on 8/21/2018.
 */

public interface IFlickrImagePresenter extends IBasePresenter {

    void congigureDataStoreModule(IDataHandler dataHandler);

    void fetchFlickrImages(String query);

    void fetchNextFlickrImages();
}
