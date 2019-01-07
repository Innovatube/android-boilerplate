package <%= package_name %>.presentation.base

import android.support.v4.app.Fragment
import <%= package_name %>.util.di.modules.FragmentModule

abstract class BaseFragment : Fragment() {
    val component by lazy {
        (activity as BaseActivity).component.plus(FragmentModule(this))
    }
}
