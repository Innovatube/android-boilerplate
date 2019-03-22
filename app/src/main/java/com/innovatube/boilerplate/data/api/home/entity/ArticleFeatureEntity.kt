package com.innovatube.boilerplate.data.api.home.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "ArticleFeatureEntities")
data class ArticleFeatureEntity(
    @SerializedName("end_datetime") var endDatetime: String,
    @PrimaryKey @SerializedName("id") var id: Long,
    @SerializedName("label") var label: String,
    @SerializedName("order") var order: Long,
    @SerializedName("start_datetime") var startDatetime: String
)
