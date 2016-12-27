package com.innovatube.boilerplate.data.local;


import com.innovatube.boilerplate.data.prefs.UserPrefs;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by TOIDV on 4/5/2016.
 */

public class PreferenceDataSourceImpl implements PreferenceDataSource {
    private final UserPrefs mUserPrefs;

    public PreferenceDataSourceImpl(UserPrefs userPrefs) {
        this.mUserPrefs = userPrefs;
    }

    @Override
    public UserPrefs getUserPrefs() {
        return mUserPrefs;
    }


}
