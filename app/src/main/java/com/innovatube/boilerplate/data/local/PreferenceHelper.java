package com.innovatube.boilerplate.data.local;


import com.innovatube.boilerplate.data.prefs.UserPrefs;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by TOIDV on 4/5/2016.
 */
@Singleton
public class PreferenceHelper {
    private final UserPrefs mUserPrefs;


    @Inject
    public PreferenceHelper(UserPrefs userPrefs) {
        this.mUserPrefs = userPrefs;
    }

    public UserPrefs getUserPrefs() {
        return mUserPrefs;
    }


}
