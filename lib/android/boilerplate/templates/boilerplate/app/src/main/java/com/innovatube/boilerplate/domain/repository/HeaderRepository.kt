package com.innovatube.boilerplate.domain.repository

import com.innovatube.boilerplate.data.api.home.entity.HeaderEntity
import com.innovatube.boilerplate.domain.model.Header
import com.innovatube.boilerplate.domain.model.HeaderInfo
import io.reactivex.Flowable

interface HeaderRepository {


    fun headers(): Flowable<List<Header>>

    fun save(headerEntity: HeaderEntity)

    fun getLikeAndReviewInfo(): Flowable<HeaderInfo>
}