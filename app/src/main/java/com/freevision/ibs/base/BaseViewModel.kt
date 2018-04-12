package com.freevision.ibs.base

import android.arch.lifecycle.ViewModel
import com.freevision.ibs.data.DataManager
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by anhtuan on 3/6/18.
 */
open class BaseViewModel: ViewModel() {
    protected val disposables = CompositeDisposable()
    @Inject
    protected lateinit var dataManager: DataManager

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}