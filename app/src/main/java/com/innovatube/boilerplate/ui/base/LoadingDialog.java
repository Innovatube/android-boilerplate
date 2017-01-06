package com.innovatube.boilerplate.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;
import com.innovatube.boilerplate.R;

/**
 * Created by quanlt on 04/01/2017.
 */

public class LoadingDialog extends DialogFragment {
    public static final String TAG = "LoadingDialog";
    private MaterialDialog dialog;

    public LoadingDialog(Context context, String title) {
        dialog = new MaterialDialog.Builder(context)
                .title(title)
                .content("Please wait")
                .progress(true, 0)
                .build();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }

    public void setTitle(String title) {
        dialog.setTitle(title);
    }
}
