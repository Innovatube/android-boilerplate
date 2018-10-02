package <%= package_name %>.presentation.home.top

import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import <%= package_name %>.domain.model.Article
import <%= package_name %>.domain.usecase.GetReviewAndLikeUseCase
import <%= package_name %>.domain.usecase.GetTopArticlesUseCase
import <%= package_name %>.presentation.common.RefreshListener
import io.reactivex.functions.Consumer
import timber.log.Timber
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject


class TopViewModel @Inject constructor(private val useCase: GetTopArticlesUseCase,
                                       private val getReviewAndLikeUseCase: GetReviewAndLikeUseCase) : RefreshListener {

    val isLoading = ObservableBoolean()
    val isError = ObservableBoolean()
    val onRefresh = MutableLiveData<Boolean>()

    val like = ObservableField<String>()
    val review = ObservableField<String>()

    val articles: MutableLiveData<List<Article>> = MutableLiveData()

    fun loadArticles(page: Int = 1) {
        isLoading.set(true)
        useCase.execute(
                Consumer {
                    this.isLoading.set(false)
                    this.isError.set(false)
                    this.articles.value = it
                },
                Consumer {
                    Timber.e(it.localizedMessage)
                    this.isLoading.set(false)
                    this.isError.set(true)
                }, page)
    }

    override fun onRefresh() {
        onRefresh.value = true
        this.articles.value = emptyList()
        loadArticles()
    }

    fun getReviewAndLikeInfo() {
        getReviewAndLikeUseCase.execute(
                Consumer {
                    review.set(NumberFormat.getInstance(Locale.ENGLISH).format(it.reviewCount))
                    like.set(NumberFormat.getInstance(Locale.ENGLISH).format(it.likeCount))
                },
                Consumer { },
                null)
    }

    fun destroy() {
        useCase.dispose()
        getReviewAndLikeUseCase.dispose()
    }
}
