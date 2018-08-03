package com.innovatube.boilerplate.domain.usecase

import com.innovatube.boilerplate.domain.SchedulerProvider
import com.innovatube.boilerplate.domain.model.Article
import com.innovatube.boilerplate.domain.repository.TopArticleRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTopArticlesUseCase @Inject constructor(
        schedulerProvider: SchedulerProvider,
        private val topArticleRepository: TopArticleRepository
) : SequentialUseCase<Int, List<Article>>(schedulerProvider) {

    public override fun buildUseCaseSingle(param: Int): Single<List<Article>> {
        return topArticleRepository.topArticles(param)
    }
}