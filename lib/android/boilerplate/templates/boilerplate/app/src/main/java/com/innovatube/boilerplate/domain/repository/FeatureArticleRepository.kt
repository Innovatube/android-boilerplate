package <%= package_name %>.domain.repository

import <%= package_name %>.domain.model.Article
import io.reactivex.Single

interface FeatureArticleRepository {

    fun featureArticles(articleFeatureId: Long, page: Int): Single<List<Article>>
}