package com.kotlin.boilerplate.di

import android.content.Context
import android.content.SharedPreferences
import com.kotlin.boilerplate.App
import com.kotlin.boilerplate.data.DataManager
import com.kotlin.boilerplate.data.prefs.PreferenceHelper
import com.kotlin.boilerplate.services.jobs.JobManagerInitializer
import com.kotlin.boilerplate.services.jobs.SchedulerJobService
import com.kotlin.boilerplate.services.networking.ExampleApiService
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
    fun provideExampleApi(context: Context): ExampleApiService {
        return ExampleApiService.create(context)
    }

    @Singleton
    @Provides
    fun provideDataManager(prefs: SharedPreferences, exampleApi: ExampleApiService): DataManager {
        return DataManager(prefs, exampleApi)
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
