package dev.jaym21.cryptowatch.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.jaym21.cryptowatch.ui.home.detail.timeperiods.*

private const val NUM_TABS = 5

class ChartViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, val currencyId: String, val isChangePositive: Boolean): FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return OneDayFragment(currencyId, isChangePositive)
            1 -> return SevenDaysFragment(currencyId, isChangePositive)
            2 -> return ThirtyDaysFragment(currencyId, isChangePositive)
            3 -> return SixMonthsFragment(currencyId, isChangePositive)
        }
        return OneYearFragment(currencyId, isChangePositive)
    }
}