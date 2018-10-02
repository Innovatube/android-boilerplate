package <%= package_name %>.util.di.modules

import <%= package_name %>.data.repository.datasource.HeaderDataStore
import <%= package_name %>.data.repository.datasource.local.HeaderLocalDataSource
import <%= package_name %>.data.repository.datasource.remote.HeaderRemoteDataSource
import dagger.Module
import dagger.Provides
import <%= package_name %>.util.di.Local
import <%= package_name %>.util.di.Remote
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