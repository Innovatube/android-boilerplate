package <%= package_name %>.domain.usecase

import <%= package_name %>.domain.SchedulerProvider
import <%= package_name %>.domain.model.Article
import <%= package_name %>.domain.repository.FeatureArticleRepository
import io.reactivex.Single
import javax.inject.Inject

class GetFeatureArticlesUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,
    private val featureArticleRepository: FeatureArticleRepository
) : SequentialUseCase<GetFeatureArticlesUseCase.Param, List<Article>>(schedulerProvider) {

    public override fun buildUseCaseSingle(param: Param): Single<List<Article>> {
        return featureArticleRepository.featureArticles(param.featureArticleId, param.pageNumber)
    }

    class Param(val featureArticleId: Long, val pageNumber: Int)
}