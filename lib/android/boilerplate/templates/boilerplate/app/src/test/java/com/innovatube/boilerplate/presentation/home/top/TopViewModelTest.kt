package <%= package_name %>.presentation.home.top

import android.arch.core.executor.testing.InstantTaskExecutorRule
import <%= package_name %>.UnitTest
import <%= package_name %>.capture
import <%= package_name %>.domain.model.Article
import <%= package_name %>.domain.usecase.GetReviewAndLikeUseCase
import <%= package_name %>.domain.usecase.GetTopArticlesUseCase
import io.reactivex.functions.Consumer
import junit.framework.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito

class TopViewModelTest : UnitTest() {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var getTopArticleUseCase: GetTopArticlesUseCase
    @Mock
    private lateinit var getReviewAndLikeUseCase: GetReviewAndLikeUseCase
    private lateinit var topViewModel: TopViewModel
    @Captor
    private lateinit var callbackOnSuccess: ArgumentCaptor<Consumer<List<Article>>>
    @Captor
    private lateinit var callbackOnError: ArgumentCaptor<Consumer<Throwable>>

    @Before
    fun setUp() {
        topViewModel = TopViewModel(getTopArticleUseCase, getReviewAndLikeUseCase)
    }

    @Test
    fun getArticles_success() {
        val article = Article(title = "Article title", url = "url")
        topViewModel.loadArticles()
        Mockito.verify(getTopArticleUseCase).execute(
            capture(callbackOnSuccess),
            capture(callbackOnError),
            ArgumentMatchers.anyInt()
        )
        Assert.assertTrue(topViewModel.isLoading.get())
        Assert.assertFalse(topViewModel.isError.get())
        callbackOnSuccess.value.accept(listOf(article))
        Assert.assertFalse(topViewModel.isLoading.get())
        Assert.assertFalse(topViewModel.isError.get())
        Assert.assertNotNull(topViewModel.articles.value)
        Assert.assertTrue(topViewModel.articles.value!!.size == 1)
    }

    @Test
    fun getArticles_fail() {
        topViewModel.loadArticles()
        Mockito.verify(getTopArticleUseCase).execute(
            capture(callbackOnSuccess),
            capture(callbackOnError),
            ArgumentMatchers.anyInt()
        )
        Assert.assertTrue(topViewModel.isLoading.get())
        Assert.assertFalse(topViewModel.isError.get())
        callbackOnError.value.accept(Exception())
        Assert.assertFalse(topViewModel.isLoading.get())
        Assert.assertTrue(topViewModel.isError.get())
        Assert.assertNull(topViewModel.articles.value)
    }
}