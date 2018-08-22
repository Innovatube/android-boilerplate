package com.innovatube.boilerplate.util.di.modules

import android.content.Context
import com.innovatube.boilerplate.data.repository.datasource.local.AppDatabase
import com.innovatube.boilerplate.data.repository.datasource.local.HeaderDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideArticleFeatureDao(context: Context): HeaderDao {
        val database = AppDatabase.getInstance(context)
        return database.articleFeatureDao()
    }

}