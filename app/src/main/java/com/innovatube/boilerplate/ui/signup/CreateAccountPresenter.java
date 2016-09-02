package com.innovatube.boilerplate.ui.signup;


import android.text.TextUtils;

import com.innovatube.boilerplate.data.DataManager;
import com.innovatube.boilerplate.data.model.UserId;
import com.innovatube.boilerplate.ui.base.BasePresenter;
import com.innovatube.boilerplate.utils.InnovatubeUtils;

import javax.inject.Inject;

import io.realm.Realm;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by TOIDV on 6/29/2016.
 */
public class CreateAccountPresenter extends BasePresenter<CreateAccountMvpView> {

    private final DataManager dataManager;
    private final Retrofit retrofit;
    private final Realm realm;

    private Subscription subscription;

    @Inject
    CreateAccountPresenter(DataManager dataManager, Retrofit retrofit, Realm realm) {
        this.dataManager = dataManager;
        this.retrofit = retrofit;
        this.realm = realm;

    }

    @Override
    public void detachView() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        super.detachView();
    }

    public void createAccount(
            String firstName,
            String lastName,
            String emailAddress,
            String password,
            String confirmPassword,
            String dob,
            String promotionCode) {
        getMvpView().showProgressDialog(true);
        subscription = dataManager.createAccount(
                firstName,
                lastName,
                emailAddress,
                password,
                confirmPassword,
                dob,
                promotionCode)
                .filter(new Func1<UserId, Boolean>() {
                    @Override
                    public Boolean call(UserId userId) {
                        return !TextUtils.isEmpty(userId.getUserId().toString());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserId>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        String error = InnovatubeUtils.getError(e, retrofit);
                        getMvpView().showProgressDialog(false);
                        getMvpView().showAlertDialog(error);

                    }

                    @Override
                    public void onNext(UserId userId) {
                        getMvpView().showProgressDialog(false);
                        if (userId != null) {
                            saveUserId(userId.getUserId());
                            dataManager.saveUserInfo(userId, realm);
                            getMvpView().redirectToHome();
                        }

                    }
                });
    }

    private void saveUserId(int userId) {
        dataManager.saveUserId(userId);
    }
}
