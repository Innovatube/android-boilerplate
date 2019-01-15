package com.innovatube.boilerplate.presentation.main

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import com.innovatube.boilerplate.R
import com.innovatube.boilerplate.databinding.ActivityMainBinding
import com.innovatube.boilerplate.presentation.base.BaseActivity
import com.innovatube.boilerplate.presentation.helper.Navigator
import com.innovatube.boilerplate.util.di.ViewModelFactory
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
