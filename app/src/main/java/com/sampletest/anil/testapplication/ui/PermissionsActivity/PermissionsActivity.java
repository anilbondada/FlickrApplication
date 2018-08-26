package com.sampletest.anil.testapplication.ui.PermissionsActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sampletest.anil.testapplication.R;
import com.sampletest.anil.testapplication.ui.PermissionsActivity.interfaces.IPermissionsPresenter;
import com.sampletest.anil.testapplication.ui.PermissionsActivity.interfaces.IPermissionsView;
import com.sampletest.anil.testapplication.ui.imagelist.FlickrImagesActivity;
import com.sampletest.anil.testapplication.utils.Utils;

public class PermissionsActivity extends AppCompatActivity implements View.OnClickListener,IPermissionsView{

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 120001;
    private Button requestPerms;
    private IPermissionsPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        requestPerms = findViewById(R.id.requestPerms);
        requestPerms.setOnClickListener(this);
        mPresenter = new PermissionsPresenter(this);
        mPresenter.checkIfAllPermissionsAccepted(this);
    }

    public void generatePermissionDialog() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void startNextActivity(){
        Intent i = new Intent(this, FlickrImagesActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
            {
                mPresenter.validatePermissions(this);
                break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.requestPerms:
            {
                mPresenter.checkIfAllPermissionsAccepted(this);
                break;
            }
        }
    }

    @Override
    public Context getViewContext() {
        return this;
    }
}
