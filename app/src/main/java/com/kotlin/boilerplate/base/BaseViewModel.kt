package com.kotlin.boilerplate.base

import android.arch.lifecycle.ViewModel
import com.kotlin.boilerplate.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by anhtuan on 3/6/18.
 */
open class BaseViewModel : ViewModel() {
    protected val disposables = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}