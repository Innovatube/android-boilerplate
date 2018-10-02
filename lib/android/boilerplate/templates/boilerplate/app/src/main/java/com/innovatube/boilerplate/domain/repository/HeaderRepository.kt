package <%= package_name %>.domain.repository

import <%= package_name %>.data.api.home.entity.HeaderEntity
import <%= package_name %>.domain.model.Header
import <%= package_name %>.domain.model.HeaderInfo
import io.reactivex.Flowable

interface HeaderRepository {


    fun headers(): Flowable<List<Header>>

    fun save(headerEntity: HeaderEntity)

    fun getLikeAndReviewInfo(): Flowable<HeaderInfo>
}