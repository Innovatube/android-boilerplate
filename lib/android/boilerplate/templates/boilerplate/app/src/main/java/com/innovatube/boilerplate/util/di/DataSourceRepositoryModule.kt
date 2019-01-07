package <%= package_name %>.util.di

import <%= package_name %>.data.api.home.HomeApi
import <%= package_name %>.data.repository.datasource.HeaderDataStore
import <%= package_name %>.data.repository.datasource.local.HeaderDao
import <%= package_name %>.data.repository.datasource.local.HeaderLocalDataSource
import <%= package_name %>.data.repository.datasource.remote.HeaderRemoteDataSource
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
