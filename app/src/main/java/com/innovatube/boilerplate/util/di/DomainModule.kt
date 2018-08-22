package com.innovatube.boilerplate.util.di

import com.innovatube.boilerplate.data.api.home.HomeApi
import com.innovatube.boilerplate.data.mapper.ArticleMapper
import com.innovatube.boilerplate.data.mapper.HeaderMapper
import com.innovatube.boilerplate.data.repository.FeatureArticleDataRepository
import com.innovatube.boilerplate.data.repository.HeaderDataRepository
import com.innovatube.boilerplate.data.repository.TopArticleDataRepository
import com.innovatube.boilerplate.data.repository.datasource.HeaderDataStore
import com.innovatube.boilerplate.domain.AppSchedulerProvider
import com.innovatube.boilerplate.domain.SchedulerProvider
import com.innovatube.boilerplate.domain.repository.FeatureArticleRepository
import com.innovatube.boilerplate.domain.repository.HeaderRepository
import com.innovatube.boilerplate.domain.repository.TopArticleRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DomainModule {

    @Singleton @Provides @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Singleton @Provides @JvmStatic
    fun provideFeatureArticleRepository(homeApi: HomeApi, mapper: ArticleMapper): FeatureArticleRepository =
            FeatureArticleDataRepository(homeApi, mapper)

    @Singleton @Provides @JvmStatic
    fun provideHeaderRepository(@Local local: HeaderDataStore, @Remote remote: HeaderDataStore, mapper: HeaderMapper): HeaderRepository =
            HeaderDataRepository(local, remote, mapper)

    @Singleton @Provides @JvmStatic
    fun provideTopArticleRepository(homeApi: HomeApi, mapper: ArticleMapper): TopArticleRepository =
            TopArticleDataRepository(homeApi, mapper)
}