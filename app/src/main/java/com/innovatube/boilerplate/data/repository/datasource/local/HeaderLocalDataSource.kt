package com.innovatube.boilerplate.data.repository.datasource.local

import com.innovatube.boilerplate.data.api.home.entity.HeaderEntity
import com.innovatube.boilerplate.data.repository.datasource.HeaderDataStore
import io.reactivex.Flowable
import javax.inject.Inject

class HeaderLocalDataSource @Inject constructor(
    private val headerDao: HeaderDao
) : HeaderDataStore {

    override fun headers(): Flowable<HeaderEntity> {
        return headerDao.all
    }

    override fun save(headerEntity: HeaderEntity) {
        headerDao.updateData(headerEntity)
    }
}