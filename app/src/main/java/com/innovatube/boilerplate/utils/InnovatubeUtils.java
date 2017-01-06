package com.innovatube.boilerplate.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.afollestad.materialdialogs.MaterialDialog;
import com.innovatube.boilerplate.consts.Consts;
import com.innovatube.boilerplate.data.model.ApiError;
import com.innovatube.boilerplate.ui.base.LoadingDialog;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.ConnectException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by TOIDV on 4/5/2016.
 */
public class InnovatubeUtils {

    public static LoadingDialog createProgress(Context context, String title) {
        return new LoadingDialog(context, title);

    }

    public static MaterialDialog createAlertDialog(Context context, String title) {
        return new MaterialDialog.Builder(context)
                .title(title)
                .positiveText("OK")
                .build();
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }


    public static String getError(Throwable e, Retrofit retrofit) {
        String errorMessage = Consts.GENERIC_ERROR;
        if (e instanceof HttpException) {
            ResponseBody body = ((HttpException) e).response().errorBody();
            Converter<ResponseBody, ApiError> responseBodyObjectConverter = retrofit.responseBodyConverter(ApiError.class, new Annotation[0]);
            try {
                ApiError error = responseBodyObjectConverter.convert(body);
                if (error != null) {
                    errorMessage = error.getMessage();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else if (e instanceof ConnectException) {
            errorMessage = Consts.SERVER_ERROR;
        }
        return errorMessage;
    }


    public static boolean isConnectivityAvailable(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.isConnectedOrConnecting();
    }
}
