package com.sampletest.anil.testapplication.datastore.local.interfaces;

import com.sampletest.anil.testapplication.model.Photo;

import java.util.List;

/**
 * Created by H211060 on 8/22/2018.
 */

public interface IFlickrImageDataResponse {
    void onFlickerImagesFecthed(List<Photo> flickrImages);
}
