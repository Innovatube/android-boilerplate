package com.innovatube.boilerplate.data.repository

import com.innovatube.boilerplate.data.api.home.HomeApi
import com.innovatube.boilerplate.data.mapper.ArticleMapper
import com.innovatube.boilerplate.domain.model.Article
import com.innovatube.boilerplate.domain.repository.FeatureArticleRepository
import io.reactivex.Single
import javax.inject.Inject

class FeatureArticleDataRepository @Inject constructor(
        private val homeApi: HomeApi,
        private val mapper: ArticleMapper
) : FeatureArticleRepository {
    override fun featureArticles(articleFeatureId: Long, page: Int): Single<List<Article>> {
        return homeApi.getFeatureArticles(articleFeatureId, page)
                .map { it -> it.article }
                .toFlowable()
                .flatMapIterable { article -> article }
                .map {
                    mapper.transform(it)
                }
                .toList()
    }

}