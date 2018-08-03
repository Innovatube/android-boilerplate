package com.innovatube.boilerplate.data.mapper

import com.innovatube.boilerplate.data.api.home.entity.ArticleEntity
import com.innovatube.boilerplate.domain.model.Article
import javax.inject.Inject

class ArticleMapper @Inject constructor() {

    fun transform(entity: ArticleEntity): Article {
        return Article(entity)
    }
}