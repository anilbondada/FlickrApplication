package com.sampletest.anil.testapplication.ui.PermissionsActivity.interfaces;

import android.content.Context;

/**
 * Created by H211060 on 8/26/2018.
 */

public interface IPermissionsPresenter {
    void checkIfAllPermissionsAccepted(Context context);
    void validatePermissions(Context context);
}
