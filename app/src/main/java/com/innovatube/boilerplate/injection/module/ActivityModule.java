package com.innovatube.boilerplate.injection.module;

import android.app.Activity;
import android.content.Context;

import com.innovatube.boilerplate.injection.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by TOIDV on 4/4/2016.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    public Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context provideApplication() {
        return mActivity;
    }

}
