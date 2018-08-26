package com.sampletest.anil.testapplication.ui.imagelist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sampletest.anil.testapplication.IApplicationDataProvider;
import com.sampletest.anil.testapplication.R;
import com.sampletest.anil.testapplication.datastore.interfaces.IDataHandler;
import com.sampletest.anil.testapplication.model.FlickerImage;
import com.sampletest.anil.testapplication.ui.base.BaseActivity;
import com.sampletest.anil.testapplication.ui.imagelist.interfaces.IFlickrImagePresenter;
import com.sampletest.anil.testapplication.ui.imagelist.interfaces.IFlickrImageView;

import java.util.List;


public class FlickrImagesActivity extends BaseActivity implements IFlickrImageView,android.support.v7.widget.SearchView.OnQueryTextListener {
    private static final String TAG = "FlickrImagesActivity";
    List<FlickerImage> mImages;
    IFlickrImagePresenter mPresenter;
    TextView tv_status_msg;
    android.support.v7.widget.RecyclerView imageView;
    RecyclerViewAdapter mImageRecylcerViewAdapter;
    Toolbar toolbar;
    LinearLayout linlaHeaderProgress;
    ProgressBar loadMoreProgressBar;
    ScrollListener scrollListener;
    android.support.v7.widget.SearchView searchView;
    private static final String defaultQuery = "test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_grid_view);
        init();
    }

    private void init(){
        linlaHeaderProgress = findViewById(R.id.linlaHeaderProgress);
        loadMoreProgressBar = findViewById(R.id.loadMoreProgressBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_status_msg = findViewById(R.id.status_msg_text);
        imageView = findViewById(R.id.flickr_image_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,3);
        imageView.setLayoutManager(gridLayoutManager);
        IDataHandler dataHandler = ((IApplicationDataProvider)getApplication()).getDatHandler(IDataHandler.DataHandlerType.DATA_HANDLER_TYPE_LOCAL);
        mImageRecylcerViewAdapter = new RecyclerViewAdapter(this,mImages,dataHandler);
        imageView.setAdapter(mImageRecylcerViewAdapter);
        scrollListener = new ScrollListener((android.support.v7.widget.GridLayoutManager)imageView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                Log.d(TAG, "onLoadMore: page:"+page+" totalItems:"+totalItemsCount);
                loadMoreProgressBar.setVisibility(View.VISIBLE);
                mPresenter.fetchNextFlickrImages();
            }
        };
        showProgressLoader();
        imageView.addOnScrollListener(scrollListener);
        mPresenter = new FlickrImagePresenter(this);
        mPresenter.congigureDataStoreModule(dataHandler);
        mPresenter.fetchFlickrImages(defaultQuery);


    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void onImagesFetchedSuccess(List<FlickerImage> images) {
        showImages();
        mImages = images;
        mImageRecylcerViewAdapter.setImageItems(mImages);
        mImageRecylcerViewAdapter.notifyDataSetChanged();
    }

    private void showImages() {
        linlaHeaderProgress.setVisibility(View.GONE);
        loadMoreProgressBar.setVisibility(View.GONE);
        tv_status_msg.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onImagesFetchedFailed(String errorMsg) {
        //show error msg
        linlaHeaderProgress.setVisibility(View.GONE);
        loadMoreProgressBar.setVisibility(View.GONE);
        tv_status_msg.setVisibility(View.VISIBLE);
        tv_status_msg.setText(errorMsg);
    }

    @Override
    public void onNextPagesFetchSuccess(List<FlickerImage> mImages) {
        showImages();
        mImageRecylcerViewAdapter.appendImageItems(mImages);
        mImageRecylcerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onNextPagesFetchFail(String errorMsg) {
        linlaHeaderProgress.setVisibility(View.GONE);
        loadMoreProgressBar.setVisibility(View.GONE);
        tv_status_msg.setVisibility(View.VISIBLE);
        tv_status_msg.setText(errorMsg);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView = (android.support.v7.widget.SearchView)  MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        showProgressLoader();
        mImageRecylcerViewAdapter.removeImages();
        mPresenter.fetchFlickrImages(query);
        searchView.clearFocus();
        return true;
    }

    private void showProgressLoader() {
        scrollListener.resetState();
        linlaHeaderProgress.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}
