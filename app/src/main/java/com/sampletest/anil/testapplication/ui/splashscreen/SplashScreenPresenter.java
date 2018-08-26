package com.sampletest.anil.testapplication.ui.splashscreen;

import android.os.Handler;
import android.os.Message;

import com.sampletest.anil.testapplication.ui.splashscreen.interfaces.ISpaceScreenView;
import com.sampletest.anil.testapplication.ui.splashscreen.interfaces.ISplashScreenPresenter;
import com.sampletest.anil.testapplication.utils.Utils;

/**
 * Created by H211060 on 8/24/2018.
 */

public class SplashScreenPresenter implements ISplashScreenPresenter {
    ISpaceScreenView mView;
    Handler handler;
    private static final int TIMER_COMPLETED = 10001;

    public SplashScreenPresenter(ISpaceScreenView view){
        this.mView = view;
    }

    @Override
    public void startTimer(long time) {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == TIMER_COMPLETED){
                    if(Utils.permissionsAccepted(mView.getViewContext())){
                        mView.startMainActivity();
                    }
                    else{
                        mView.startPermissionActivity();
                    }
                }
                return true;
            }
        });
        Message msg = handler.obtainMessage();
        msg.what = TIMER_COMPLETED;
        handler.sendMessageDelayed(msg,time);
    }


}
