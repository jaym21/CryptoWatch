package dev.jaym21.cryptowatch.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.jaym21.cryptowatch.ui.watchlist.WatchlistFourFragment
import dev.jaym21.cryptowatch.ui.watchlist.WatchlistOneFragment
import dev.jaym21.cryptowatch.ui.watchlist.WatchlistThreeFragment
import dev.jaym21.cryptowatch.ui.watchlist.WatchlistTwoFragment

private const val WATCHLIST_TABS = 4

class WatchlistViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return WATCHLIST_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when(position) {
            0 -> return WatchlistOneFragment()
            1 -> return WatchlistTwoFragment()
            2 -> return WatchlistThreeFragment()
        }
        return WatchlistFourFragment()
    }
}