package <%= package_name %>.data.repository.datasource.remote

import <%= package_name %>.data.api.home.HomeApi
import <%= package_name %>.data.api.home.entity.HeaderEntity
import <%= package_name %>.data.repository.datasource.HeaderDataStore
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