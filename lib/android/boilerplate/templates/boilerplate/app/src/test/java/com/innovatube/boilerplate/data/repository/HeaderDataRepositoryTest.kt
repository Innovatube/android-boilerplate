package <%= package_name %>.data.repository

import <%= package_name %>.UnitTest
import <%= package_name %>.data.api.home.entity.ArticleFeatureEntity
import <%= package_name %>.data.api.home.entity.HeaderEntity
import <%= package_name %>.data.mapper.HeaderMapper
import <%= package_name %>.data.repository.datasource.HeaderDataStore
import <%= package_name %>.domain.model.Header
import <%= package_name %>.domain.repository.HeaderRepository
import io.reactivex.Flowable
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy

class HeaderDataRepositoryTest : UnitTest() {
    private lateinit var headerRepository: HeaderRepository
    @Mock
    private lateinit var headerLocalDataSource: HeaderDataStore
    @Mock
    private lateinit var headerRemoteDataSource: HeaderDataStore
    @Spy
    private lateinit var headerMapper: HeaderMapper
    private lateinit var listOfEntity: List<ArticleFeatureEntity>
    private lateinit var listHeaders: List<Header>
    private lateinit var listHeadersWithoutFeatureArticles: List<Header>
    private lateinit var headerEntity: HeaderEntity

    @Before
    fun setup() {
        headerRepository =
                HeaderDataRepository(headerLocalDataSource, headerRemoteDataSource, headerMapper)
    }

    @Before
    fun setupMockData() {
        listOfEntity = listOf(ArticleFeatureEntity("", 1, "abc", 1, ""))
        headerEntity = HeaderEntity(articleFeatures = listOfEntity)
        val top = Header(Header.Type.TOP)
        val feature = Header(Header.Type.FEATURE, "abc", 1)
        listHeadersWithoutFeatureArticles = listOf(top)
        listHeaders = listOf(top, feature)
    }

    private fun setHeadersNotAvailable(dataSource: HeaderDataStore) {
        BDDMockito.given(dataSource.headers()).willReturn(Flowable.error(Error("Not available")))
    }

    private fun setHeadersAvailable(
        dataSource: HeaderDataStore,
        headerEntity: HeaderEntity? = null
    ) {
        if (headerEntity == null) {
            BDDMockito.given(dataSource.headers()).willReturn(Flowable.never())
        } else {
            BDDMockito.given(dataSource.headers())
                .willReturn(Flowable.just(headerEntity).concatWith(Flowable.never()))
        }
    }

    @Test
    fun getHeaders_fromDataSource_remoteDifferentWithLocal() {
        // Given that local data source
        setHeadersAvailable(headerLocalDataSource, HeaderEntity())

        // And the remote data source have data
        setHeadersAvailable(headerRemoteDataSource, headerEntity)

        val testObserver = headerRepository.headers().test()
        testObserver.assertNoErrors()

        Mockito.verify(headerLocalDataSource).headers()
        Mockito.verify(headerRemoteDataSource).headers()
        // Save to local
        Mockito.verify(headerLocalDataSource).save(headerEntity)

        // Firstly, it get data from local to display
        testObserver.assertValueAt(0, listHeadersWithoutFeatureArticles)
        // Then server return, it updates the data by
        testObserver.assertValueAt(1, listHeaders)
    }

    @Test
    fun getHeaders_fromLocalDataSourceOnly() {
        setHeadersAvailable(headerLocalDataSource, headerEntity)
        setHeadersNotAvailable(headerRemoteDataSource)

        val testObserver = headerRepository.headers().test()
        testObserver.assertNoErrors()

        Mockito.verify(headerLocalDataSource).headers()
        Mockito.verify(headerRemoteDataSource).headers()

        testObserver.assertValue(listHeaders)
    }

    @Test
    fun getHeaders_fromRemoteDataSourceOnly() {
        setHeadersAvailable(headerRemoteDataSource, headerEntity)
        setHeadersNotAvailable(headerLocalDataSource)

        val testObserver = headerRepository.headers().test()
        testObserver.assertNoErrors()

        Mockito.verify(headerLocalDataSource).headers()
        Mockito.verify(headerRemoteDataSource).headers()

        testObserver.assertValue(listHeaders)
    }

    @Test
    fun getHeader_fromDataSource_remoteSameWithLocal() {
        setHeadersAvailable(headerRemoteDataSource, headerEntity)
        setHeadersAvailable(headerLocalDataSource, headerEntity)

        val testObserver = headerRepository.headers().test()
        testObserver.assertNoErrors()
        testObserver.assertValue(listHeaders)

        Mockito.verify(headerRemoteDataSource).headers()
        Mockito.verify(headerLocalDataSource).headers()
        Mockito.verify(headerLocalDataSource).save(headerEntity)
        Mockito.verify(headerMapper).transform(listOfEntity)
    }

    @Test
    fun getHeader_unAvailable() {
        setHeadersNotAvailable(headerRemoteDataSource)
        setHeadersNotAvailable(headerLocalDataSource)
        val testObserver = headerRepository.headers().test()
        Mockito.verify(headerRemoteDataSource).headers()
        Mockito.verify(headerLocalDataSource).headers()

        testObserver.awaitTerminalEvent()
        testObserver.assertNoValues()
        MatcherAssert.assertThat(testObserver.errorCount(), CoreMatchers.`is`(1))
    }
}