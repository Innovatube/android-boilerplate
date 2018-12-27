package com.innovatube.boilerplate.domain.usecase

import com.innovatube.boilerplate.domain.SchedulerProvider
import com.innovatube.boilerplate.domain.model.Article
import com.innovatube.boilerplate.domain.repository.FeatureArticleRepository
import io.reactivex.Single
import javax.inject.Inject

class GetFeatureArticlesUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,
    private val featureArticleRepository: FeatureArticleRepository
) : SequentialUseCase<GetFeatureArticlesUseCase.Param, List<Article>>(schedulerProvider) {

    public override fun buildUseCaseSingle(param: Param): Single<List<Article>> {
        return featureArticleRepository.featureArticles(param.featureArticleId, param.pageNumber)
    }

    class Param(val featureArticleId: Long, val pageNumber: Int)
}