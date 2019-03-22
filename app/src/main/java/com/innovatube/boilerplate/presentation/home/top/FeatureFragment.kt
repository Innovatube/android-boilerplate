package com.innovatube.boilerplate.presentation.home.top

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.innovatube.boilerplate.AppExecutors
import com.innovatube.boilerplate.databinding.FragmentFeatureBinding
import com.innovatube.boilerplate.domain.model.Header
import com.innovatube.boilerplate.presentation.base.BaseFragment
import com.innovatube.boilerplate.presentation.helper.DialogHelper
import com.innovatube.boilerplate.presentation.helper.Navigator
import com.innovatube.boilerplate.presentation.home.adapter.FeatureArticleAdapter
import com.innovatube.boilerplate.presentation.listener.EndlessRecyclerOnScrollListener
import com.innovatube.boilerplate.util.di.ViewModelFactory
import javax.inject.Inject

class FeatureFragment : BaseFragment() {

    private lateinit var binding: FragmentFeatureBinding
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var appExecutors: AppExecutors
    private var adapter: FeatureArticleAdapter? = null
    private lateinit var header: Header
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var dialogHelper: DialogHelper

    private val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(FeatureViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        header = arguments?.getParcelable(ARGS_HEADER) ?: return
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeatureBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvArticle.layoutManager = LinearLayoutManager(
            activity,
            RecyclerView.VERTICAL,
            false
        )
        if (adapter == null) {
            adapter = FeatureArticleAdapter(
                appExecutors
            ) {
            }
        }
        binding.rvArticle.adapter = adapter

        val scrollListener = object : EndlessRecyclerOnScrollListener(
            binding.rvArticle.layoutManager as LinearLayoutManager
        ) {
            override fun onLoadMore(currentPage: Int) {
                viewModel.loadArticles(header.featureId, currentPage)
            }
        }
        binding.rvArticle.addOnScrollListener(scrollListener)

        viewModel.onRefresh.observe(this, Observer {
            it?.let { scrollListener.reset() }
        })
        viewModel.loadArticles(header.featureId)
        viewModel.articles.observe(this, Observer {
            it?.let {
                adapter?.submitList(it)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQ_QUOTE_ARTICLE && resultCode == Activity.RESULT_OK) {
        }
    }

    companion object {
        private const val ARGS_HEADER = "args_header"
        private const val RQ_QUOTE_ARTICLE = 0x1

        fun newInstance(header: Header): FeatureFragment {
            val fragment = FeatureFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARGS_HEADER, header)
            fragment.arguments = bundle
            return fragment
        }
    }
}
