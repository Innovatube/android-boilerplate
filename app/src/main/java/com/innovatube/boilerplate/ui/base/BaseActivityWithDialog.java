package com.innovatube.boilerplate.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.innovatube.boilerplate.R;
import com.innovatube.boilerplate.utils.InnovatubeUtils;


/**
 * Created by TOIDV on 4/4/2016.
 */
public abstract class BaseActivityWithDialog extends BaseActivity implements BaseMvpView {

    protected MaterialDialog alertDialog;
    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createProgressDialog();
        createAlertDialog();
        setupDialogTitle();
    }


    @Override
    public void createProgressDialog() {
        loadingDialog = InnovatubeUtils.createProgress(this, getString(R.string.title_dialog));
    }

    @Override
    public void createAlertDialog() {
        alertDialog = InnovatubeUtils.createAlertDialog(this, getString(R.string.title_dialog));
    }

    @Override
    public void showProgressDialog(boolean value) {
        if (value) {
            loadingDialog.show(getSupportFragmentManager(), loadingDialog.TAG);
        } else {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showAlertDialog(String errorMessage) {
        alertDialog.setContent(errorMessage);
        alertDialog.show();
    }

    @Override
    public void dismissDialog() {
        if (getSupportFragmentManager().findFragmentByTag(LoadingDialog.TAG) != null) {
            alertDialog.dismiss();
        }

        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    protected abstract void setupDialogTitle();

    @Override
    protected void onDestroy() {
        dismissDialog();
        super.onDestroy();
    }
}
