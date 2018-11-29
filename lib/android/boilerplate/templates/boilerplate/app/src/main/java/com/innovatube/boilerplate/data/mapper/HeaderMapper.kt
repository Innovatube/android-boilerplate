package <%= package_name %>.data.mapper

import <%= package_name %>.data.api.home.entity.ArticleFeatureEntity
import <%= package_name %>.domain.model.Header
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