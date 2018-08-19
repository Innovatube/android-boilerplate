package com.innovatube.boilerplate.data.api.home.entity

import com.google.gson.annotations.SerializedName
import com.innovatube.boilerplate.data.api.common.entity.LinksEntity

data class ArticlesEntity(
        @SerializedName("_links") val links: LinksEntity,
        @SerializedName("contents") val article: List<ArticleEntity>,
        @SerializedName("page") val page: Int,
        @SerializedName("size") val size: Int,
        @SerializedName("total_hits") val totalHits: Int
)