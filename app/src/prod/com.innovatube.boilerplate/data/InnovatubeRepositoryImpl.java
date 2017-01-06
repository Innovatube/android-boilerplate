package com.innovatube.boilerplate.data;

import com.innovatube.boilerplate.data.local.LocalDataSource;
import com.innovatube.boilerplate.data.local.PreferenceDataSource;
import com.innovatube.boilerplate.data.model.UserId;
import com.innovatube.boilerplate.data.prefs.UserPrefs;
import com.innovatube.boilerplate.data.remote.RemoteDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.realm.Realm;
import rx.Observable;

/**
 * Created by TOIDV on 4/5/2016.
 */

public class InnovatubeRepositoryImpl implements InnovatubeRepository {
    private final RemoteDataSource remoteDataSource;
    private final PreferenceDataSource preferenceDataSource;
    private final LocalDataSource localDataSource;


    public InnovatubeRepositoryImpl(RemoteDataSource remoteDataSource,
                                    PreferenceDataSource preferenceDataSource,
                                    LocalDataSource localDataSource) {
        this.remoteDataSource = remoteDataSource;
        this.preferenceDataSource = preferenceDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public boolean isLogin() {
        UserPrefs userPrefs = preferenceDataSource.getUserPrefs();
        return userPrefs.getUserId() > 0;
    }


    private String getToken() {
        UserPrefs userPrefs = preferenceDataSource.getUserPrefs();
        return userPrefs.getAccessToken();
    }

    @Override
    public int getUserId() {
        UserPrefs userPrefs = preferenceDataSource.getUserPrefs();
        return userPrefs.getUserId();

    }

    @Override
    public void saveUserId(int userId) {
        UserPrefs userPrefs = preferenceDataSource.getUserPrefs();
        userPrefs.setUserId(userId);
    }

    @Override
    public void logout() {
        clearPreference();
    }

    private void clearPreference() {
        UserPrefs userPrefs = preferenceDataSource.getUserPrefs();
        userPrefs.setUserId(-1);
        userPrefs.setFirstName("");
        userPrefs.setLastName("");
        userPrefs.setAccessToken("");
        userPrefs.setProfilePicture("");
        userPrefs.setEmail("");
    }

    @Override
    public Observable<UserId> createAccount(String firstName,
                                            String lastName,
                                            String emailAddress,
                                            String password,
                                            String confirmPassword,
                                            String dob,
                                            String promotionCode) {
        return remoteDataSource.createJobSeekerAccount(firstName,
                lastName, emailAddress, password, confirmPassword, dob, promotionCode);
    }

    @Override
    public void saveUserInfo(UserId userId, Realm realm) {
        localDataSource.saveUserInfo(userId, realm);
    }
}
