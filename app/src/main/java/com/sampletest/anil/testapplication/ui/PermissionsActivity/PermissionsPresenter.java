package com.sampletest.anil.testapplication.ui.PermissionsActivity;

import android.content.Context;
import android.content.pm.PackageManager;

import com.sampletest.anil.testapplication.ui.PermissionsActivity.interfaces.IPermissionsPresenter;
import com.sampletest.anil.testapplication.ui.PermissionsActivity.interfaces.IPermissionsView;
import com.sampletest.anil.testapplication.utils.Utils;

/**
 * Created by H211060 on 8/26/2018.
 */

public class PermissionsPresenter implements IPermissionsPresenter {
    IPermissionsView mView;
    public PermissionsPresenter(IPermissionsView view){
        this.mView = view;
    }
    @Override
    public void checkIfAllPermissionsAccepted(Context context) {
        if(checkAppPermissions(context)){
            mView.startNextActivity();
        }
        else{
            mView.generatePermissionDialog();
        }

    }

    private boolean checkAppPermissions(Context context){
        if(Utils.permissionsAccepted(context)){
            return true;
        }
        else{
            PackageManager pm = context.getPackageManager();
            int hasPerm = pm.checkPermission(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    context.getPackageName());
            if (hasPerm != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            else{
                return true;
            }
        }
    }
}
