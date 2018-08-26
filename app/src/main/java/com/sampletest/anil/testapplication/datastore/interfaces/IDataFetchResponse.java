package com.sampletest.anil.testapplication.datastore.interfaces;

import com.sampletest.anil.testapplication.model.FlickerImage;

import java.util.List;

/**
 * Created by H211060 on 8/21/2018.
 */

public interface IDataFetchResponse {

    void onSuccessResponse(List<FlickerImage> images);

    void onFailedResponse(String errorMsg);

}
