package <%= package_name %>.util.di.modules

import android.content.Context
import <%= package_name %>.data.repository.datasource.local.AppDatabase
import <%= package_name %>.data.repository.datasource.local.HeaderDao
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