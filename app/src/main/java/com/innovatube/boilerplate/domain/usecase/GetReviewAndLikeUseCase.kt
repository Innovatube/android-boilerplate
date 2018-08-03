package com.innovatube.boilerplate.domain.usecase

import com.innovatube.boilerplate.domain.SchedulerProvider
import com.innovatube.boilerplate.domain.model.HeaderInfo
import com.innovatube.boilerplate.domain.repository.HeaderRepository
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