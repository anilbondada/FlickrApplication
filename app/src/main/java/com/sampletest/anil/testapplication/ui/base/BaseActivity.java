package com.sampletest.anil.testapplication.ui.base;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.sampletest.anil.testapplication.ui.base.interfaces.IBaseView;

/**
 * Created by H211060 on 8/21/2018.
 */

public class BaseActivity extends AppCompatActivity implements IBaseView{
    @Override
    public Context getViewContext() {
        return this;
    }
}
