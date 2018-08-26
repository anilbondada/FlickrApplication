package com.sampletest.anil.testapplication.ui.imagelist.interfaces;

import com.sampletest.anil.testapplication.model.FlickerImage;
import com.sampletest.anil.testapplication.ui.base.interfaces.IBaseView;

import java.util.List;

/**
 * Created by H211060 on 8/21/2018.
 */

public interface IFlickrImageView extends IBaseView{
    void onImagesFetchedSuccess(List<FlickerImage> images);

    void onImagesFetchedFailed(String errorMsg);

    void onNextPagesFetchSuccess(List<FlickerImage> mImages);

    void onNextPagesFetchFail(String errorMsg);
}
