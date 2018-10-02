package <%= package_name %>.presentation.home.top

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import <%= package_name %>.AppExecutors
import <%= package_name %>.databinding.FragmentTopBinding
import <%= package_name %>.presentation.base.BaseFragment
import <%= package_name %>.presentation.helper.Navigator
import <%= package_name %>.presentation.listener.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.fragment_top.*
import javax.inject.Inject

class TopFragment : BaseFragment() {

    private lateinit var binding: FragmentTopBinding
    @Inject
    lateinit var viewModel: TopViewModel
    @Inject
    lateinit var layoutManager: LinearLayoutManager
    @Inject
    lateinit var appExecutors: AppExecutors
    private var adapter: TopRecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        component.inject(this)
        binding = FragmentTopBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvArticle.layoutManager = layoutManager
        if (adapter == null) {
            adapter = TopRecyclerViewAdapter(appExecutors) {

            }
        }
        rvArticle.adapter = adapter
        val scrollListener = object : EndlessRecyclerOnScrollListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                viewModel.loadArticles(currentPage)
            }
        }
        rvArticle.addOnScrollListener(scrollListener)
        viewModel.onRefresh.observe(this, Observer {
            it?.let { scrollListener.reset() }
        })
        viewModel.loadArticles()
        viewModel.getReviewAndLikeInfo()

        viewModel.articles.observe(this, Observer {
            it?.let {
                adapter?.submitList(it)
            }
        })

    }

    override fun onDestroy() {
        viewModel.destroy()
        super.onDestroy()
    }


    companion object {
        fun newInstance() = TopFragment()
    }
}
