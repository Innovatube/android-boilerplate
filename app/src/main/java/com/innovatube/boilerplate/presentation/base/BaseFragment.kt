package com.innovatube.boilerplate.presentation.base

import android.support.v4.app.Fragment
import com.innovatube.boilerplate.util.di.modules.FragmentModule

abstract class BaseFragment : Fragment() {
    val component by lazy {
        (activity as BaseActivity).component.plus(FragmentModule(this))
    }
}
