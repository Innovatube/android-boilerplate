package com.innovatube.boilerplate.ui.base;

/**
 * Created by TOIDV on 4/5/2016.
 */
public interface BaseMvpView extends MvpView {

    void createProgressDialog();

    void createAlertDialog();

    void showProgressDialog(boolean value);

    void showAlertDialog(String errorMessage);

    void dismissDialog();




}
