package <%= package_name %>.util.di.modules

import <%= package_name %>.data .repository.FeatureArticleDataRepository
import <%= package_name %>.data .repository.HeaderDataRepository
import <%= package_name %>.data .repository.TopArticleDataRepository
import <%= package_name %>.domain.AppSchedulerProvider
import <%= package_name %>.domain.SchedulerProvider
import <%= package_name %>.domain.repository.FeatureArticleRepository
import <%= package_name %>.domain.repository.HeaderRepository
import <%= package_name %>.domain.repository.TopArticleRepository
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