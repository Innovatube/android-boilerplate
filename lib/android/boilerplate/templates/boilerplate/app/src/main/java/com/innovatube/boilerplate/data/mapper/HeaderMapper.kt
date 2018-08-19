package com.innovatube.boilerplate.data.mapper

import com.innovatube.boilerplate.data.api.home.entity.ArticleFeatureEntity
import com.innovatube.boilerplate.domain.model.Header
import javax.inject.Inject

class HeaderMapper @Inject constructor() {

    fun transform(entities: List<ArticleFeatureEntity>): List<Header> {
        val headers: MutableList<Header> = mutableListOf()
        for (entity in entities) {
            headers.add(Header(entity))
        }
        return headers

    }
}