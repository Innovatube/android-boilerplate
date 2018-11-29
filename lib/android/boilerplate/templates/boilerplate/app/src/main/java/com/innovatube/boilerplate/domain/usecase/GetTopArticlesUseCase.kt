package <%= package_name %>.domain.usecase

import <%= package_name %>.domain.SchedulerProvider
import <%= package_name %>.domain.model.Article
import <%= package_name %>.domain.repository.TopArticleRepository
import io.reactivex.Single
import javax.inject.Inject

class GetTopArticlesUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,
    private val topArticleRepository: TopArticleRepository
) : SequentialUseCase<Int, List<Article>>(schedulerProvider) {

    public override fun buildUseCaseSingle(param: Int): Single<List<Article>> {
        return topArticleRepository.topArticles(param)
    }
}