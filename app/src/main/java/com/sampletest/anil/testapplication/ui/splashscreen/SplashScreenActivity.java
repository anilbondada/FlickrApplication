package com.sampletest.anil.testapplication.ui.splashscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sampletest.anil.testapplication.R;
import com.sampletest.anil.testapplication.ui.PermissionsActivity.PermissionsActivity;
import com.sampletest.anil.testapplication.ui.imagelist.FlickrImagesActivity;
import com.sampletest.anil.testapplication.ui.splashscreen.interfaces.ISpaceScreenView;
import com.sampletest.anil.testapplication.ui.splashscreen.interfaces.ISplashScreenPresenter;

public class SplashScreenActivity extends AppCompatActivity implements ISpaceScreenView{

    private long delayTimeInMilliSec = 2*1000;
    private ISplashScreenPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        presenter = new SplashScreenPresenter(this);


    }

    public void startMainActivity(){
        Intent i = new Intent(this,FlickrImagesActivity.class);
        startActivity(i);
        finish();

    }

    public void startPermissionActivity() {
        Intent i = new Intent(this,PermissionsActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.startTimer(delayTimeInMilliSec);
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
