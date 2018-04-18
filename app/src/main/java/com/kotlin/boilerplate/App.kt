package com.kotlin.boilerplate

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import com.blankj.utilcode.util.Utils
import com.freevision.ibs.di.DaggerAppComponent
import com.kotlin.boilerplate.services.jobs.JobManagerInitializer
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var jobManagerInitializer: JobManagerInitializer

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(realmConfiguration)

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        jobManagerInitializer.initialize(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return mFragmentInjector
    }
}
