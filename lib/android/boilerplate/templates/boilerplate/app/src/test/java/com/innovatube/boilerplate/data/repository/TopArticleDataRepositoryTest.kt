package <%= package_name %>.data.repository

import <%= package_name %>.UnitTest
import <%= package_name %>.data.api.common.entity.LinksEntity
import <%= package_name %>.data.api.home.HomeApi
import <%= package_name %>.data.api.home.entity.ArticleEntity
import <%= package_name %>.data.api.home.entity.ArticlesEntity
import <%= package_name %>.data.mapper.ArticleMapper
import <%= package_name %>.domain.model.Article
import <%= package_name %>.domain.repository.TopArticleRepository
import io.reactivex.Single
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Spy

class TopArticleDataRepositoryTest : UnitTest() {

    private lateinit var topArticleDataRepository: TopArticleRepository
    @Spy
    private lateinit var mapper: ArticleMapper
    @Mock
    private lateinit var homeApi: HomeApi

    @Mock
    private lateinit var linkEntity: LinksEntity
    @Mock
    private lateinit var articlesEntity: ArticlesEntity

    @Before
    fun setUp() {
        topArticleDataRepository = TopArticleDataRepository(homeApi, mapper)
    }

    @Test
    fun getTopArticles_callFromApi() {
        BDDMockito.given(homeApi.getTopArticles(BDDMockito.anyInt()))
            .willReturn(Single.just(articlesEntity))
        topArticleDataRepository.topArticles(BDDMockito.anyInt())
        Mockito.verify<HomeApi>(homeApi).getTopArticles(BDDMockito.anyInt())
    }

    @Test
    fun getTopArticles_complete() {
        BDDMockito.given(homeApi.getTopArticles(BDDMockito.anyInt()))
            .willReturn(Single.just(articlesEntity))
        val testObserver = topArticleDataRepository.topArticles(1).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertComplete()
    }

    @Test
    fun getTopArticles_fail() {
        val response = Throwable("Error response")
        BDDMockito.given(homeApi.getTopArticles(BDDMockito.anyInt()))
            .willReturn(Single.error(response))
        val testObserver = topArticleDataRepository.topArticles(1).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertError(response)
    }

    @Test
    fun getTopArticles_success() {
        val article = Article(title = "test title", url = "url")
        val articleEntity = ArticleEntity(title = "test title", url = "url")
        val articlesEntity = ArticlesEntity(linkEntity, listOf(articleEntity), 1, 1, 1)
        BDDMockito.given(homeApi.getTopArticles(1)).willReturn(Single.just(articlesEntity))
        val testObserver = topArticleDataRepository.topArticles(1).test()
        testObserver.awaitTerminalEvent()
        testObserver.assertNoErrors()
        testObserver.assertValues(listOf(article))

        Assert.assertEquals(testObserver.values()[0][0].title, "test title")
        Assert.assertEquals(testObserver.values()[0][0].url, "url")

        Mockito.verify(homeApi).getTopArticles(1)
        Mockito.verify(mapper).transform(articleEntity)
    }
}