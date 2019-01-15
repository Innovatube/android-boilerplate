package com.innovatube.boilerplate.util.di

import android.app.Application
import com.innovatube.boilerplate.data.repository.datasource.local.AppDatabase
import com.innovatube.boilerplate.data.repository.datasource.local.HeaderDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides @Singleton
    fun provideDatabase(app: Application) = AppDatabase.getInstance(app)

    @Provides @Singleton
    fun provideArticleFeatureDao(db: AppDatabase): HeaderDao = db.articleFeatureDao()
}
