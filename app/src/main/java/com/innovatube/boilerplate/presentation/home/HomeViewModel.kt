package com.innovatube.boilerplate.presentation.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.innovatube.boilerplate.domain.model.Header
import com.innovatube.boilerplate.domain.usecase.GetHeadersUseCase
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