package com.innovatube.boilerplate.data.repository.datasource

import com.innovatube.boilerplate.data.api.home.entity.HeaderEntity
import io.reactivex.Flowable

interface HeaderDataStore {

    fun headers(): Flowable<HeaderEntity>

    fun save(headerEntity: HeaderEntity)
}