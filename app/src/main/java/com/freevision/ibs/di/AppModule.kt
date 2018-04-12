package com.freevision.ibs.di

import android.content.Context
import android.content.SharedPreferences
import com.freevision.ibs.App
import com.freevision.ibs.data.DataManager
import com.freevision.ibs.data.prefs.PreferenceHelper
import com.freevision.ibs.services.jobs.JobManagerInitializer
import com.freevision.ibs.services.jobs.SchedulerJobService
import com.freevision.ibs.services.networking.IbsApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * This is where you will inject application-wide dependencies.
 */
@Module
class AppModule {

    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideDefaultPrefs(context: Context): SharedPreferences {
        return PreferenceHelper.defaultPrefs(context)
    }

    @Singleton
    @Provides
    fun provideIbsApi(context: Context): IbsApiService {
        return IbsApiService.create(context)
    }

    @Singleton
    @Provides
    fun provideDataManager(prefs: SharedPreferences, ibsApi: IbsApiService): DataManager {
        return DataManager(prefs, ibsApi)
    }

    @Singleton
    @Provides
    fun provideSchedulerJobService(): SchedulerJobService {
        return SchedulerJobService()
    }

    @Singleton
    @Provides
    fun provideManagerInitializer(): JobManagerInitializer {
        return JobManagerInitializer()
    }
}
