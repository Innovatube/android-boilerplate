package com.innovatube.boilerplate.data.repository.datasource.remote

import com.innovatube.boilerplate.data.api.home.HomeApi
import com.innovatube.boilerplate.data.api.home.entity.HeaderEntity
import com.innovatube.boilerplate.data.repository.datasource.HeaderDataStore
import io.reactivex.Flowable
import javax.inject.Inject

class HeaderRemoteDataSource @Inject constructor(
        private val homeApi: HomeApi
) : HeaderDataStore {
    override fun save(headerEntity: HeaderEntity) {
        throw UnsupportedOperationException()
    }

    override fun headers(): Flowable<HeaderEntity> {
        return homeApi.getHeaders("hairsalon")
                .toFlowable()
    }
}