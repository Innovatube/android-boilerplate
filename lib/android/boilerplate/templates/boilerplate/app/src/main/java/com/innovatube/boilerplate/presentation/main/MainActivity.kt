package <%= package_name %>.presentation.main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import <%= package_name %>.R
import <%= package_name %>.databinding.ActivityMainBinding
import <%= package_name %>.presentation.base.BaseActivity
import <%= package_name %>.presentation.helper.Navigator
import <%= package_name %>.util.di.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = mainViewModel
        setSupportActionBar(toolbar)
        fabMenu.setOnMenuButtonClickListener {
            fabMenu.toggle(true)
        }
        fabMenu.setClosedOnTouchOutside(true)
        navigator.navigateToHomeFragment()
    }
}
