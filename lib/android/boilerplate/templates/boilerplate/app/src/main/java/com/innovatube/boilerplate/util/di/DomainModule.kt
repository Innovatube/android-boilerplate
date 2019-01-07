package <%= package_name %>.util.di

import <%= package_name %>.data.api.home.HomeApi
import <%= package_name %>.data.mapper.ArticleMapper
import <%= package_name %>.data.mapper.HeaderMapper
import <%= package_name %>.data.repository.FeatureArticleDataRepository
import <%= package_name %>.data.repository.HeaderDataRepository
import <%= package_name %>.data.repository.TopArticleDataRepository
import <%= package_name %>.data.repository.datasource.HeaderDataStore
import <%= package_name %>.domain.AppSchedulerProvider
import <%= package_name %>.domain.SchedulerProvider
import <%= package_name %>.domain.repository.FeatureArticleRepository
import <%= package_name %>.domain.repository.HeaderRepository
import <%= package_name %>.domain.repository.TopArticleRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DomainModule {

    @Singleton @Provides @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Singleton @Provides @JvmStatic
    fun provideFeatureArticleRepository(
        homeApi: HomeApi,
        mapper: ArticleMapper
    ): FeatureArticleRepository = FeatureArticleDataRepository(homeApi, mapper)

    @Singleton @Provides @JvmStatic
    fun provideHeaderRepository(
        @Local local: HeaderDataStore,
        @Remote remote: HeaderDataStore,
        mapper: HeaderMapper
    ): HeaderRepository = HeaderDataRepository(local, remote, mapper)

    @Singleton @Provides @JvmStatic
    fun provideTopArticleRepository(
        homeApi: HomeApi,
        mapper: ArticleMapper
    ): TopArticleRepository = TopArticleDataRepository(homeApi, mapper)
}
