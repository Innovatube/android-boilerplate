package <%= package_name %>.data.api.common.entity

import com.google.gson.annotations.SerializedName

data class PrevEntity(
        @SerializedName("href") val href: String? = null
)