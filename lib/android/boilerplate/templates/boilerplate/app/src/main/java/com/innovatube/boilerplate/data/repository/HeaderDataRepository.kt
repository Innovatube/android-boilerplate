package <%= package_name %>.data.repository

import <%= package_name %>.data.api.home.entity.HeaderEntity
import <%= package_name %>.data.mapper.HeaderMapper
import <%= package_name %>.data.repository.datasource.HeaderDataStore
import <%= package_name %>.domain.model.Header
import <%= package_name %>.domain.model.HeaderInfo
import <%= package_name %>.domain.repository.HeaderRepository
import <%= package_name %>.util.di.Local
import <%= package_name %>.util.di.Remote
import io.reactivex.Flowable
import javax.inject.Inject

class HeaderDataRepository @Inject constructor(
        @Local private val headerLocalDataSource: HeaderDataStore,
        @Remote private val headerRemoteDataSource: HeaderDataStore,
        private val mapper: HeaderMapper
) : HeaderRepository {

    override fun getLikeAndReviewInfo(): Flowable<HeaderInfo> {
        return headerLocalDataSource.headers()
                .map { header -> HeaderInfo(header.reviewCount, header.likeCount) }
    }

    override fun save(headerEntity: HeaderEntity) {
        headerLocalDataSource.save(headerEntity)
    }


    override fun headers(): Flowable<List<Header>> {
        val top = Header(Header.Type.TOP)
        return Flowable.mergeDelayError(
                headerLocalDataSource.headers(),
                headerRemoteDataSource.headers()
                        .doOnNext { headerEntity ->
                            save(headerEntity)
                        })
                .map { it.articleFeatures }
                .distinct()
                .map {
                    val headers = mutableListOf(top)
                    headers.addAll(mapper.transform(it))
                    return@map headers
                }

    }
}