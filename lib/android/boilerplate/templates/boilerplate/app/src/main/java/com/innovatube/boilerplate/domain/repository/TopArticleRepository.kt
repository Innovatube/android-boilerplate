package <%= package_name %>.domain.repository

import <%= package_name %>.domain.model.Article
import io.reactivex.Single

interface TopArticleRepository {

    fun topArticles(page: Int): Single<List<Article>>
}