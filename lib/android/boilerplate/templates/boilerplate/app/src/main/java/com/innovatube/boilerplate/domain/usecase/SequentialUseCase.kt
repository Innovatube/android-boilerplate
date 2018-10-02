package <%= package_name %>.domain.usecase

import <%= package_name %>.domain.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

abstract class SequentialUseCase<in PARAM, RESPONSE> protected constructor(
        private val schedulerProvider: SchedulerProvider
) {
    private val compositeDisposable = CompositeDisposable()

    fun execute(onSuccess: Consumer<RESPONSE>, onError: Consumer<Throwable>, param: PARAM) {
        val disposable = buildUseCaseSingle(param)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(onSuccess, onError)
        compositeDisposable.add(disposable)
    }

    protected abstract fun buildUseCaseSingle(param: PARAM): Single<RESPONSE>

    fun dispose() {
        compositeDisposable.clear()
    }
}