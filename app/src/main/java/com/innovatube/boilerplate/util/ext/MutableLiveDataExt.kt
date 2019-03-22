package com.innovatube.boilerplate.util.ext

import androidx.lifecycle.MutableLiveData

fun <T : Any> MutableLiveData<List<T>>.addAll(newList: List<T>) {
    val list = mutableListOf<T>()
    value?.let {
        list.addAll(it)
    }
    list.addAll(newList)
    value = list
}