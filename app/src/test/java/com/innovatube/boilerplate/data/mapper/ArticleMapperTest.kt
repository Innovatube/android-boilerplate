package com.innovatube.boilerplate.data.mapper

import com.innovatube.boilerplate.MockServerTest
import com.innovatube.boilerplate.data.api.home.HomeApi
import com.innovatube.boilerplate.data.repository.FeatureArticleDataRepository
import com.innovatube.boilerplate.data.repository.TopArticleDataRepository
import com.innovatube.boilerplate.domain.repository.FeatureArticleRepository
import com.innovatube.boilerplate.domain.repository.TopArticleRepository
import com.innovatube.boilerplate.utils.TestUtils
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
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
        val dummy = TestUtils.getStringFromPath(this, "success_articles_features_hairsalon_1_1.json")

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