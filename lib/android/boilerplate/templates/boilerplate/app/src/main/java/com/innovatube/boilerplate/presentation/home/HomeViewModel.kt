package <%= package_name %>.presentation.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import <%= package_name %>.domain.model.Header
import <%= package_name %>.domain.usecase.GetHeadersUseCase
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    val context: Context,
    private val headersUseCase: GetHeadersUseCase
) : ViewModel() {

    var headers: MutableLiveData<List<Header>> = MutableLiveData()

    fun getHeaders() {

        headersUseCase.execute(
            Consumer {
                headers.value = it
            },
            Consumer {
                Timber.e(it)
            },
            null
        )
    }

    fun destroy() {
        headersUseCase.dispose()
    }
}