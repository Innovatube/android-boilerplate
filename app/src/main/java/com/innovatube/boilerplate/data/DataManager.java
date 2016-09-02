package com.innovatube.boilerplate.data;

import com.innovatube.boilerplate.data.local.PreferenceHelper;
import com.innovatube.boilerplate.data.local.RealmHelper;
import com.innovatube.boilerplate.data.model.UserId;
import com.innovatube.boilerplate.data.prefs.UserPrefs;
import com.innovatube.boilerplate.data.remote.InnovatubeService;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * Created by TOIDV on 4/5/2016.
 */

@Singleton
public class DataManager {
    private final InnovatubeService innovatubeService;
    private final PreferenceHelper preferenceHelper;
    private final RealmHelper realmHelper;

    @Inject
    public DataManager(InnovatubeService inploiService, PreferenceHelper preferenceHelper, RealmHelper realmHelper) {
        this.innovatubeService = inploiService;
        this.preferenceHelper = preferenceHelper;
        this.realmHelper = realmHelper;
    }

    public boolean isLogin() {
        UserPrefs userPrefs = preferenceHelper.getUserPrefs();
        return userPrefs.getUserId() > 0;
    }


    private String getToken() {
        UserPrefs userPrefs = preferenceHelper.getUserPrefs();
        return userPrefs.getAccessToken();
    }

    public int getUserId() {
        UserPrefs userPrefs = preferenceHelper.getUserPrefs();
        return userPrefs.getUserId();

    }


    public void saveUserId(int userId) {
        UserPrefs userPrefs = preferenceHelper.getUserPrefs();
        userPrefs.setUserId(userId);
    }

    public void logout() {
        clearPreference();
    }

    private void clearPreference() {
        UserPrefs userPrefs = preferenceHelper.getUserPrefs();
        userPrefs.setUserId(-1);
        userPrefs.setFirstName("");
        userPrefs.setLastName("");
        userPrefs.setAccessToken("");
        userPrefs.setProfilePicture("");
        userPrefs.setEmail("");
    }

    public Observable<UserId> createAccount(String firstName,
                                            String lastName,
                                            String emailAddress,
                                            String password,
                                            String confirmPassword,
                                            String dob,
                                            String promotionCode) {
        return innovatubeService.createJobSeekerAccount(firstName, lastName, emailAddress, password, confirmPassword, dob, promotionCode);
    }

    public void saveUserInfo(UserId userId) {
        realmHelper.saveUserInfo(userId);
    }
}
