package <%= package_name %>.data.mapper

import <%= package_name %>.data.api.home.entity.ArticleEntity
import <%= package_name %>.domain.model.Article
import javax.inject.Inject

class ArticleMapper @Inject constructor() {

    fun transform(entity: ArticleEntity): Article {
        return Article(entity)
    }
}