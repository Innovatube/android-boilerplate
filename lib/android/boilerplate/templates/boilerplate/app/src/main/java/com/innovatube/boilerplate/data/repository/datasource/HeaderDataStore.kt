package <%= package_name %>.data.repository.datasource

import <%= package_name %>.data.api.home.entity.HeaderEntity
import io.reactivex.Flowable

interface HeaderDataStore {

    fun headers(): Flowable<HeaderEntity>

    fun save(headerEntity: HeaderEntity)
}