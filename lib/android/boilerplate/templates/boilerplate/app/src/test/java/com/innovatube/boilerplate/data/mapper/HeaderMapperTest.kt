package <%= package_name %>.data.mapper

import <%= package_name %>.MockServerTest
import <%= package_name %>.data.api.home.HomeApi
import <%= package_name %>.data.api.home.entity.HeaderEntity
import <%= package_name %>.data.repository.HeaderDataRepository
import <%= package_name %>.data.repository.datasource.local.HeaderDao
import <%= package_name %>.data.repository.datasource.local.HeaderLocalDataSource
import <%= package_name %>.data.repository.datasource.remote.HeaderRemoteDataSource
import <%= package_name %>.domain.model.Header
import <%= package_name %>.domain.repository.HeaderRepository
import <%= package_name %>.utils.TestUtils
import io.reactivex.Flowable
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class HeaderMapperTest : MockServerTest() {
    private lateinit var headerMapper: HeaderMapper
    private lateinit var homeApi: HomeApi
    private lateinit var repository: HeaderRepository
    private lateinit var headerRemoteDataSource: HeaderRemoteDataSource
    private lateinit var headerLocalDataSource: HeaderLocalDataSource
    private lateinit var mockWebServer: MockWebServer
    @Mock
    private
    lateinit var dao: HeaderDao
    @Mock
    private lateinit var headerEntity: HeaderEntity

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        homeApi = createApiClient(HomeApi::class.java, mockWebServer.url("/").toString())
        headerMapper = HeaderMapper()
        headerRemoteDataSource = HeaderRemoteDataSource(homeApi)
        headerLocalDataSource = HeaderLocalDataSource(dao)
        repository =
                HeaderDataRepository(headerRemoteDataSource, headerLocalDataSource, headerMapper)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun testTransformArticleFeatureEntityToHeaderSuccess() {
        Mockito.`when`(dao.all).thenReturn(Flowable.just(headerEntity))
        val dummy = TestUtils.getStringFromPath(this, "success_headers.json")

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(dummy)
        mockWebServer.enqueue(mockResponse)

        val result = repository.headers().blockingFirst()
        assertEquals("/headers?type=hairsalon", mockWebServer.takeRequest().path)
        assertNotNull(result)
        assertEquals(3, result.size)

        assertEquals(Header.Type.TOP, result[0].type)
        assertEquals(Header.Type.FEATURE, result[1].type)
        assertEquals("Feature", result[1].label)
    }
}