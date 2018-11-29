package <%= package_name %>.data.mapper

import <%= package_name %>.MockServerTest
import <%= package_name %>.data.api.home.HomeApi
import <%= package_name %>.data.repository.FeatureArticleDataRepository
import <%= package_name %>.data.repository.TopArticleDataRepository
import <%= package_name %>.domain.repository.FeatureArticleRepository
import <%= package_name %>.domain.repository.TopArticleRepository
import <%= package_name %>.utils.TestUtils
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class ArticleMapperTest : MockServerTest() {

    private lateinit var articleMapper: ArticleMapper
    private lateinit var homeApi: HomeApi
    private lateinit var topArticleRepository: TopArticleRepository
    private lateinit var featureArticleRepository: FeatureArticleRepository
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        homeApi = createApiClient(HomeApi::class.java, mockWebServer.url("/").toString())
        articleMapper = ArticleMapper()
        topArticleRepository = TopArticleDataRepository(homeApi, articleMapper)
        featureArticleRepository = FeatureArticleDataRepository(homeApi, articleMapper)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun transformTopArticles() {
        val dummy = TestUtils.getStringFromPath(this, "success_articles_top_hairsalon_1.json")

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(dummy)
        mockWebServer.enqueue(mockResponse)

        val result = topArticleRepository.topArticles(1).blockingGet()
        assertEquals("/articles/top/hairsalon/1", mockWebServer.takeRequest().path)
        assertNotNull(result)

        assertEquals(14, result.size)
        assertEquals("Duis autem vel eum iriure dolor", result[0].title)
    }

    @Test
    fun transformFeatureArticles() {
        val dummy =
            TestUtils.getStringFromPath(this, "success_articles_features_hairsalon_1_1.json")

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setHeader("Content-Type", "application/json")
            .setBody(dummy)
        mockWebServer.enqueue(mockResponse)

        val result = featureArticleRepository.featureArticles(1, 1).blockingGet()
        assertEquals("/articles/features/hairsalon/1/1", mockWebServer.takeRequest().path)
        assertNotNull(result)

        assertEquals(14, result.size)
        assertEquals("Duis autem vel eum iriure dolor", result[0].title)
    }
}