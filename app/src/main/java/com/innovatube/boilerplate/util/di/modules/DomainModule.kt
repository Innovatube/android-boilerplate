package com.innovatube.boilerplate.util.di.modules

import com.innovatube.boilerplate.data.repository.FeatureArticleDataRepository
import com.innovatube.boilerplate.data.repository.HeaderDataRepository
import com.innovatube.boilerplate.data.repository.TopArticleDataRepository
import com.innovatube.boilerplate.domain.AppSchedulerProvider
import com.innovatube.boilerplate.domain.SchedulerProvider
import com.innovatube.boilerplate.domain.repository.FeatureArticleRepository
import com.innovatube.boilerplate.domain.repository.HeaderRepository
import com.innovatube.boilerplate.domain.repository.TopArticleRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideTopArticleRepository(topArticleDataRepository: TopArticleDataRepository): TopArticleRepository {
        return topArticleDataRepository
    }

    @Provides
    @Singleton
    fun provideFeatureArticleRepository(featureArticleDataRepository: FeatureArticleDataRepository): FeatureArticleRepository {
        return featureArticleDataRepository
    }

    @Provides
    @Singleton
    fun provideHomeRepository(headersDataRepository: HeaderDataRepository): HeaderRepository {
        return headersDataRepository
    }
}