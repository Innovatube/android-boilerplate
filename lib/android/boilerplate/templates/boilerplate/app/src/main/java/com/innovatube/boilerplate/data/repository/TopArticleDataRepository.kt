package <%= package_name %>.data.repository

import <%= package_name %>.data.api.home.HomeApi
import <%= package_name %>.data.mapper.ArticleMapper
import <%= package_name %>.domain.model.Article
import <%= package_name %>.domain.repository.TopArticleRepository
import io.reactivex.Single
import javax.inject.Inject

class TopArticleDataRepository @Inject constructor(
    private val homeApi: HomeApi,
    private val mapper: ArticleMapper
) : TopArticleRepository {
    override fun topArticles(page: Int): Single<List<Article>> {
        return homeApi.getTopArticles(page)
            .map { it -> it.article }
            .toFlowable()
            .flatMapIterable { article -> article }
            .map {
                mapper.transform(it)
            }
            .toList()
    }
}