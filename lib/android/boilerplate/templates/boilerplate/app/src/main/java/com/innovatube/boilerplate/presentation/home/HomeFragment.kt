package <%= package_name %>.presentation.home

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import <%= package_name %>.databinding.FragmentHomeBinding
import <%= package_name %>.domain.model.Header
import <%= package_name %>.presentation.base.BaseFragment
import <%= package_name %>.presentation.home.adapter.HomePagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {
    private var currentIndex: Int = 0
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomePagerAdapter
    @Inject
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        component.inject(this)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.headers.observe(this, Observer<List<Header>> {
            it?.let {
                setupViewPager(it)
            }
        })
        viewModel.getHeaders()
    }

    private fun setupViewPager(headers: List<Header>) {
        homeAdapter = HomePagerAdapter(this.activity, childFragmentManager, headers)
        vpHome.adapter = homeAdapter
        tabLayout.setupWithViewPager(vpHome)
    }

    override fun onDestroy() {
        viewModel.destroy()
        super.onDestroy()
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
