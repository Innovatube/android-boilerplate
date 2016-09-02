package com.innovatube.boilerplate.injection.module;

import android.app.Application;
import android.content.Context;

import com.innovatube.boilerplate.data.local.RealmHelper;
import com.innovatube.boilerplate.data.remote.InnovatubeService;
import com.innovatube.boilerplate.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import retrofit2.Retrofit;

/**
 * Created by TOIDV on 4/4/2016.
 */

@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    InnovatubeService provideInnovatubeService(Retrofit retrofit) {
        return retrofit.create(InnovatubeService.class);
    }

    @Provides
    RealmHelper provideRealmHelper() {
        return new RealmHelper();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofitInstance() {
        return InnovatubeService.Creator.newRetrofitInstance();
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        return Realm.getDefaultInstance();
    }


}
