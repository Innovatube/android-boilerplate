package com.innovatube.boilerplate.data.api.home.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.innovatube.boilerplate.data.api.common.entity.LinksEntity
import com.innovatube.boilerplate.data.repository.datasource.local.HeaderTypeConverter

@Entity(tableName = "HeaderEntity")
data class HeaderEntity(
    @Ignore
    @SerializedName("_links") var links: LinksEntity = LinksEntity(),
    @TypeConverters(HeaderTypeConverter::class)
    @SerializedName("article_features") var articleFeatures: List<ArticleFeatureEntity> = listOf(),
    @SerializedName("review_count") var reviewCount: Long = 0,
    @SerializedName("like_count") var likeCount: Long = 0,
    @SerializedName("type") var type: String = "",
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
)