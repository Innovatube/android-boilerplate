package com.innovatube.boilerplate.data.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.innovatube.boilerplate.injection.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by TOIDV on 4/5/2016.
 */
@Singleton
public class UserPrefs {
    private final SharedPreferences mSharePreferences;
    private final SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    @Inject
    public UserPrefs(@ApplicationContext Context context) {
        mSharePreferences = context.getSharedPreferences(UserPrefConst.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mSharePreferences.edit();
    }

    public String getAccessToken() {
        return mSharePreferences.getString(UserPrefConst.ACCESS_TOKEN, "");
    }

    public void setAccessToken(String accessToken) {
        mEditor.putString(UserPrefConst.ACCESS_TOKEN, accessToken);
        mEditor.commit();

    }

    public String getProfilePicture() {
        return mSharePreferences.getString(UserPrefConst.AVATAR_URL, "");
    }

    public void setProfilePicture(String url) {
        mEditor.putString(UserPrefConst.AVATAR_URL, url);
        mEditor.commit();
    }

    public String getFirstName() {
        return mSharePreferences.getString(UserPrefConst.FIRST_NAME, "");
    }

    public void setFirstName(String firstName) {
        mEditor.putString(UserPrefConst.FIRST_NAME, firstName);
        mEditor.commit();
    }

    public String getLastName() {
        return mSharePreferences.getString(UserPrefConst.LAST_NAME, "");
    }

    public void setLastName(String lastName) {
        mEditor.putString(UserPrefConst.LAST_NAME, lastName);
        mEditor.commit();
    }

    public String getEmail() {
        return mSharePreferences.getString(UserPrefConst.USER_EMAIL, "");
    }

    public void setEmail(String email) {
        mEditor.putString(UserPrefConst.USER_EMAIL, email);
        mEditor.commit();
    }

    public int getUserId() {
        return mSharePreferences.getInt(UserPrefConst.USER_ID, 0);
    }

    public void setUserId(int userId) {
        mEditor.putInt(UserPrefConst.USER_ID, userId);
        mEditor.commit();
    }


}
