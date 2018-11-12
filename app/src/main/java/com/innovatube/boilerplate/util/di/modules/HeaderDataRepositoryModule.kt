package com.innovatube.boilerplate.util.di.modules

import com.innovatube.boilerplate.data.repository.datasource.HeaderDataStore
import com.innovatube.boilerplate.data.repository.datasource.local.HeaderLocalDataSource
import com.innovatube.boilerplate.data.repository.datasource.remote.HeaderRemoteDataSource
import com.innovatube.boilerplate.util.di.Local
import com.innovatube.boilerplate.util.di.Remote
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HeaderDataRepositoryModule {

    @Provides
    @Singleton
    @Local
    fun provideLocalDataSource(localDataSource: HeaderLocalDataSource): HeaderDataStore {
        return localDataSource
    }

    @Provides
    @Singleton
    @Remote
    fun provideRemoteDataSource(remoteDataSource: HeaderRemoteDataSource): HeaderDataStore {
        return remoteDataSource
    }
}