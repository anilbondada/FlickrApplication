package com.sampletest.anil.testapplication.ui.imagelist;

import com.sampletest.anil.testapplication.IApplicationDataProvider;
import com.sampletest.anil.testapplication.datastore.interfaces.IDataFetchResponse;
import com.sampletest.anil.testapplication.datastore.interfaces.IDataHandler;
import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkParamsDetails;
import com.sampletest.anil.testapplication.model.FlickerImage;
import com.sampletest.anil.testapplication.ui.imagelist.interfaces.IFlickrImagePresenter;
import com.sampletest.anil.testapplication.ui.imagelist.interfaces.IFlickrImageView;
import com.sampletest.anil.testapplication.utils.Constants;
import com.sampletest.anil.testapplication.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by H211060 on 8/22/2018.
 */

public class FlickrImagePresenter implements IFlickrImagePresenter, IDataFetchResponse,INetworkParamsDetails{

    IFlickrImageView mView;
    IDataHandler mDataHandler;
    List<FlickerImage> mImages;
    HashMap<String,String> params;
    String lastSearchedQuery;

    public FlickrImagePresenter(IFlickrImageView view){
        this.mView = view;
        params = new HashMap<>();
    }

    @Override
    public void congigureDataStoreModule(IDataHandler dataHandler) {
        this.mDataHandler = dataHandler;
    }

    public void fetchNextFlickrImages(){
        if(mDataHandler != null){
            Utils.shutDownExecuter();
            params.put(Constants.API_PARMAMS_SEARCH_TEXT,lastSearchedQuery);
            mDataHandler.fetchImages(mView.getViewContext(), this, new IDataFetchResponse() {
                @Override
                public void onSuccessResponse(List<FlickerImage> images) {
                    //here we need to append to the list of existing data
                    mView.onNextPagesFetchSuccess(images);

                }

                @Override
                public void onFailedResponse(String errorMsg) {
                    mView.onNextPagesFetchFail(errorMsg);
                }
            });
        }
    }

    @Override
    public void fetchFlickrImages(String query) {
        if(mDataHandler != null){
            lastSearchedQuery = query;
            params.put(Constants.API_PARMAMS_SEARCH_TEXT,query);
            mDataHandler.fetchImages(mView.getViewContext(),this,this);
        }
    }

    @Override
    public void onSuccessResponse(List<FlickerImage> images) {
        mImages = images;
        mView.onImagesFetchedSuccess(mImages);

    }

    @Override
    public void onFailedResponse(String errorMsg) {
        mView.onImagesFetchedFailed(errorMsg);
    }

    @Override
    public HashMap<String, String> getHeaders() {
        String flikerKey = ((IApplicationDataProvider)mView.getViewContext().getApplicationContext()).getFlickrAPIKey();
        HashMap<String,String> headers = new HashMap<>();
        headers.put(Constants.API_KEY,flikerKey);
        return headers;
    }

    @Override
    public HashMap<String, String> getParams() {
        String flikerKey = ((IApplicationDataProvider)mView.getViewContext().getApplicationContext()).getFlickrAPIKey();
        params.put(Constants.API_KEY,flikerKey);
        params.put(Constants.API_FORMAT,Constants.API_FORMAT_JSON);
        params.put(Constants.API_PARMAMS_NO_JSON_CALLBACK,"1");
        params.put(Constants.API_PARMAMS_SAFE_SEARCH,"1");
        params.put(Constants.API_PARMAMS_METHOD_URL,"flickr.photos.search");
        int currentPage = Utils.getCurrentPage();
        if(currentPage < Utils.getTotalNumberOfPages()) {
            params.put(Constants.PAGES, String.valueOf(currentPage + 1));
        }
        return params;
    }

    @Override
    public String getUrl()
    {
        String url = ((IApplicationDataProvider)mView.getViewContext().getApplicationContext()).getBaseUrl();
        return url;
    }
}
