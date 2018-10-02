package <%= package_name %>.domain.usecase

import <%= package_name %>.domain.SchedulerProvider
import <%= package_name %>.domain.model.Header
import <%= package_name %>.domain.repository.HeaderRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetHeadersUseCase @Inject constructor(
        schedulerProvider: SchedulerProvider,
        private val headersRepository: HeaderRepository
) : MergeDelayErrorUseCase<String?, List<Header>>(schedulerProvider) {

    public override fun buildUseCaseFlowable(param: String?): Flowable<List<Header>> {
        return headersRepository.headers()
    }
}