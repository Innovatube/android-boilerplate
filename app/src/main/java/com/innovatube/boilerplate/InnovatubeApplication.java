package com.innovatube.boilerplate;

import android.app.Application;
import android.content.Context;
import android.support.annotation.VisibleForTesting;

import com.facebook.stetho.Stetho;
import com.innovatube.boilerplate.injection.component.ApplicationComponent;
import com.innovatube.boilerplate.injection.component.DaggerApplicationComponent;
import com.innovatube.boilerplate.injection.module.ApplicationModule;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by TOIDV on 4/4/2016.
 */
public class InnovatubeApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    public static InnovatubeApplication get(Context context) {
        return (InnovatubeApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());


    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public synchronized ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;

    }

    @VisibleForTesting
    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.mApplicationComponent = applicationComponent;
    }
}
