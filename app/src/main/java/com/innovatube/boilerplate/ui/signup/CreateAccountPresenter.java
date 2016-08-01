package com.innovatube.boilerplate.ui.signup;


import com.innovatube.boilerplate.data.DataManager;
import com.innovatube.boilerplate.data.model.UserId;
import com.innovatube.boilerplate.ui.base.BasePresenter;
import com.innovatube.boilerplate.utils.InnovatubeUtils;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by TOIDV on 6/29/2016.
 */
public class CreateAccountPresenter extends BasePresenter<CreateAccountMvpView> {

    private final DataManager mDataManager;
    private final Retrofit mRetrofit;

    private Subscription mSubscription;

    @Inject
    CreateAccountPresenter(DataManager dataManager, Retrofit retrofit) {
        this.mDataManager = dataManager;
        this.mRetrofit = retrofit;
    }

    @Override
    public void detachView() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
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
        mSubscription = mDataManager.createAccount(
                firstName,
                lastName,
                emailAddress,
                password,
                confirmPassword,
                dob,
                promotionCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserId>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        String error = InnovatubeUtils.getError(e, mRetrofit);
                        getMvpView().showProgressDialog(false);
                        getMvpView().showAlertDialog(error);

                    }

                    @Override
                    public void onNext(UserId userId) {
                        if (userId != null) {
                            saveUserId(userId.getUserId());
                            getMvpView().redirectToHome();
                        }

                    }
                });
    }

    private void saveUserId(int userId) {
        mDataManager.saveUserId(userId);
    }
}
