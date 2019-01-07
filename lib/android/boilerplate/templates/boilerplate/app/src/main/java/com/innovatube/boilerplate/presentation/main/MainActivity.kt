package <%= package_name %>.presentation.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import <%= package_name %>.R
import <%= package_name %>.databinding.ActivityMainBinding
import <%= package_name %>.presentation.base.BaseActivity
import <%= package_name %>.presentation.helper.FragmentHelper
import <%= package_name %>.presentation.helper.Navigator
import <%= package_name %>.presentation.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var fragmentHelper: FragmentHelper
    @Inject
    lateinit var header: MainHeaderViewModel

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // FIXME testing android lint
        component.inject(this)
        binding.header = header
        setSupportActionBar(toolbar)
        fabMenu.setOnMenuButtonClickListener {
            fabMenu.toggle(true)
        }
        fabMenu.setClosedOnTouchOutside(true)
        fragmentHelper.replaceFragment(HomeFragment.newInstance(), R.id.mainContent, false)
    }
}
