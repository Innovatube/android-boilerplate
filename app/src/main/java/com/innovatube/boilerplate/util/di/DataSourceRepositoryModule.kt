package com.innovatube.boilerplate.util.di

import com.innovatube.boilerplate.data.api.home.HomeApi
import com.innovatube.boilerplate.data.repository.datasource.HeaderDataStore
import com.innovatube.boilerplate.data.repository.datasource.local.HeaderDao
import com.innovatube.boilerplate.data.repository.datasource.local.HeaderLocalDataSource
import com.innovatube.boilerplate.data.repository.datasource.remote.HeaderRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceRepositoryModule {

    @Provides @Singleton @Local
    fun provideLocalDataSource(headerDao: HeaderDao): HeaderDataStore =
            HeaderLocalDataSource(headerDao)

    @Provides @Singleton @Remote
    fun provideRemoteDataSource(homeApi: HomeApi): HeaderDataStore =
            HeaderRemoteDataSource(homeApi)
}
