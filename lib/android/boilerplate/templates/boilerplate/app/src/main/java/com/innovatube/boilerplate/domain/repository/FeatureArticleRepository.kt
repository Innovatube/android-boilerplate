package com.innovatube.boilerplate.domain.repository

import com.innovatube.boilerplate.domain.model.Article
import io.reactivex.Single

interface FeatureArticleRepository {

    fun featureArticles(articleFeatureId: Long, page: Int): Single<List<Article>>
}