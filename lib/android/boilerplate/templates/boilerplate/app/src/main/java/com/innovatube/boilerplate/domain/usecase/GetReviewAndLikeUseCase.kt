package <%= package_name %>.domain.usecase

import <%= package_name %>.domain.SchedulerProvider
import <%= package_name %>.domain.model.HeaderInfo
import <%= package_name %>.domain.repository.HeaderRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetReviewAndLikeUseCase @Inject constructor(
    schedulerProvider: SchedulerProvider,
    private val headersRepository: HeaderRepository
) : FlowableUseCase<String?, HeaderInfo>(schedulerProvider) {

    public override fun buildUseCaseFlowable(param: String?): Flowable<HeaderInfo> {
        return headersRepository.getLikeAndReviewInfo()
    }
}