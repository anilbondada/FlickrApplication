package com.sampletest.anil.testapplication.ui.imagelist;

import android.content.Context;

import com.sampletest.anil.testapplication.datastore.interfaces.IDataFetchResponse;
import com.sampletest.anil.testapplication.datastore.interfaces.IDataHandler;
import com.sampletest.anil.testapplication.datastore.network.interfaces.INetworkParamsDetails;
import com.sampletest.anil.testapplication.model.FlickerImage;
import com.sampletest.anil.testapplication.ui.imagelist.interfaces.IFlickrImagePresenter;
import com.sampletest.anil.testapplication.ui.imagelist.interfaces.IFlickrImageView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Created by H211060 on 8/22/2018.
 */
public class FlickrImagePresenterTest {
    FlickrImagePresenter mPresenter;
    @Mock
    IFlickrImageView mView;
    IDataHandler mDataHandler;
    List<FlickerImage> mImages;
    @Mock
    Context context;
    private final String errorMsg = "No results found for this search";
    @Before
    public void setup(){
        context = Mockito.mock(Context.class);
        mView = Mockito.mock(IFlickrImageView.class);
        mDataHandler = Mockito.mock(IDataHandler.class);
        mPresenter = new FlickrImagePresenter(mView);
        mPresenter.congigureDataStoreModule(mDataHandler);
        mImages = getNewImages();
        Mockito.when(mView.getViewContext()).thenReturn(context);

    }
    @Test
    public void congigureDataStoreModule() throws Exception {
        mPresenter = new FlickrImagePresenter(mView);
        mPresenter.congigureDataStoreModule(mDataHandler);
    }

    @Test
    public void fetchFlickrImages() throws Exception {

        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                    IDataFetchResponse callback = invocation.getArgument(2);
                    callback.onSuccessResponse(mImages);
                    return null;
            }
        };

        Mockito.doAnswer(answer).when(mDataHandler).fetchImages(any(Context.class),any(INetworkParamsDetails.class),any(IDataFetchResponse.class));
        mPresenter.fetchFlickrImages("test");
        Mockito.verify(mView).onImagesFetchedSuccess(mImages);
    }

    @Test
    public void onSuccessResponse() throws Exception {
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                IDataFetchResponse callback = invocation.getArgument(2);
                callback.onSuccessResponse(mImages);
                return null;
            }
        };
        Mockito.doAnswer(answer).when(mDataHandler).fetchImages(any(Context.class),any(INetworkParamsDetails.class),any(IDataFetchResponse.class));

        mPresenter.fetchFlickrImages("test");
        Mockito.verify(mView).onImagesFetchedSuccess(mImages);
    }

    @Test
    public void onFailedResponse() throws Exception {
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                IDataFetchResponse callback = invocation.getArgument(2);
                callback.onFailedResponse(errorMsg);
                return null;
            }
        };
        Mockito.when(mView.getViewContext()).thenReturn(context);
        Mockito.doAnswer(answer).when(mDataHandler).fetchImages(any(Context.class),any(INetworkParamsDetails.class),any(IDataFetchResponse.class));
        mPresenter.fetchFlickrImages("test");
        Mockito.verify(mView).onImagesFetchedFailed(errorMsg);
    }

    @Test
    public void fetchNextImagesSuccess(){
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                IDataFetchResponse callback = invocation.getArgument(2);
                callback.onSuccessResponse(mImages);
                return null;
            }
        };

        Mockito.doAnswer(answer).when(mDataHandler).fetchImages(any(Context.class),any(INetworkParamsDetails.class),any(IDataFetchResponse.class));
        mPresenter.fetchNextFlickrImages();
        Mockito.verify(mView).onNextPagesFetchSuccess(mImages);
    }

    @Test
    public void fetchNextImagesFailure(){
        Answer answer = new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                IDataFetchResponse callback = invocation.getArgument(2);
                callback.onFailedResponse(errorMsg);
                return null;
            }
        };
        Mockito.when(mView.getViewContext()).thenReturn(context);
        Mockito.doAnswer(answer).when(mDataHandler).fetchImages(any(Context.class),any(INetworkParamsDetails.class),any(IDataFetchResponse.class));
        mPresenter.fetchNextFlickrImages();
        Mockito.verify(mView).onNextPagesFetchFail(errorMsg);
    }

    public List<FlickerImage> getNewImages() {
        List<FlickerImage> mImages = new ArrayList<FlickerImage>();
        mImages.add(new FlickerImage("1",null,"test1"));
        mImages.add(new FlickerImage("2",null,"test2"));
        mImages.add(new FlickerImage("1",null,"test3"));
        mImages.add(new FlickerImage("2",null,"test4"));
        mImages.add(new FlickerImage("1",null,"test5"));
        mImages.add(new FlickerImage("2",null,"test6"));
        mImages.add(new FlickerImage("1",null,"test7"));
        mImages.add(new FlickerImage("2",null,"test8"));
        mImages.add(new FlickerImage("1",null,"test9"));
        mImages.add(new FlickerImage("2",null,"test10"));
        mImages.add(new FlickerImage("1",null,"test11"));
        mImages.add(new FlickerImage("2",null,"test12"));
        return mImages;
    }
}