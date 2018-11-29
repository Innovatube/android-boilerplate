package <%= package_name %>.data.api.common.entity

import com.google.gson.annotations.SerializedName

class SelfEntity(
    @SerializedName("href") var href: String? = null
)
