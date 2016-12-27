package com.innovatube.boilerplate.data;

import com.innovatube.boilerplate.data.model.UserId;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by quanlt on 27/12/2016.
 */

public interface InnovatubeRepository {
    boolean isLogin();
    void logout();
    int getUserId();
    void saveUserId(int userId);
    Observable<UserId> createAccount(String firstName,
                                     String lastName,
                                     String emailAddress,
                                     String password,
                                     String confirmPassword,
                                     String dob,
                                     String promotionCode);
    void saveUserInfo(UserId userId, Realm realm);
}
