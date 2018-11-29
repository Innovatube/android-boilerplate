package com.innovatube.boilerplate.domain.usecase

import com.innovatube.boilerplate.domain.SchedulerProvider
import com.innovatube.boilerplate.domain.model.Header
import com.innovatube.boilerplate.domain.repository.HeaderRepository
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