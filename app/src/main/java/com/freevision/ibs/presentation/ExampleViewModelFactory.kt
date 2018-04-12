package com.freevision.ibs.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.freevision.ibs.data.DataManager

/**
 * Created by anhtuan on 3/6/18.
 */

class ExampleViewModelFactory(private val dataManager: DataManager) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExampleViewModel::class.java)) {
            return ExampleViewModel(dataManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}