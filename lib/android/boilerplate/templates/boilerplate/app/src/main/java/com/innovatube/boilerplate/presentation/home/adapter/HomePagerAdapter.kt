package <%= package_name %>.presentation.home.adapter

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import <%= package_name %>.R
import <%= package_name %>.domain.model.Header
import <%= package_name %>.domain.model.Header.Type.FEATURE
import <%= package_name %>.domain.model.Header.Type.TOP
import <%= package_name %>.presentation.home.top.FeatureFragment
import <%= package_name %>.presentation.home.top.TopFragment

class HomePagerAdapter(private val context: Context?, fm: FragmentManager, private val fragmentTitles: List<Header>) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val header = fragmentTitles[getRealPosition(position)]
        return when (header.type) {
            TOP -> TopFragment.newInstance()
            FEATURE -> FeatureFragment.newInstance(header)
        }
    }

    override fun getCount(): Int {
        return fragmentTitles.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return getTitleAt(position)
    }

    private fun getRealPosition(position: Int): Int {
        return position % fragmentTitles.size
    }

    private fun getTitleAt(position: Int): String? {
        return when (fragmentTitles[getRealPosition(position)].type) {
            TOP -> context?.getString(R.string.header_top)
            FEATURE -> fragmentTitles[position % fragmentTitles.size].label
        }
    }


}
