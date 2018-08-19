package com.innovatube.boilerplate.domain.repository

import com.innovatube.boilerplate.domain.model.Article
import io.reactivex.Single

interface TopArticleRepository {

    fun topArticles(page: Int): Single<List<Article>>
}