package com.sampletest.anil.testapplication.ui.PermissionsActivity;

import android.content.Context;

import com.sampletest.anil.testapplication.ui.PermissionsActivity.interfaces.IPermissionsPresenter;
import com.sampletest.anil.testapplication.ui.PermissionsActivity.interfaces.IPermissionsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by H211060 on 8/26/2018.
 */
public class PermissionsPresenterTest {
    @Mock
    Context context;
    @Mock
    IPermissionsView mView;

    private IPermissionsPresenter presenter;

    @Before
    public void setup(){
        context = Mockito.mock(Context.class);
        mView = Mockito.mock(IPermissionsView.class);
    }
    @Test
    public void checkIfAllPermissionsAccepted() throws Exception {
        presenter = new PermissionsPresenter(mView);
        presenter.checkIfAllPermissionsAccepted(context);
        Mockito.verify(mView).startNextActivity();

    }

}