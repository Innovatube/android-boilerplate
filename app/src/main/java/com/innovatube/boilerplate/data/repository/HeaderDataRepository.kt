package com.innovatube.boilerplate.data.repository

import com.innovatube.boilerplate.data.api.home.entity.HeaderEntity
import com.innovatube.boilerplate.data.mapper.HeaderMapper
import com.innovatube.boilerplate.data.repository.datasource.HeaderDataStore
import com.innovatube.boilerplate.domain.model.Header
import com.innovatube.boilerplate.domain.model.HeaderInfo
import com.innovatube.boilerplate.domain.repository.HeaderRepository
import com.innovatube.boilerplate.util.di.Local
import com.innovatube.boilerplate.util.di.Remote
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