package dev.jaym21.cryptowatch.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import dev.jaym21.cryptowatch.adapters.WatchlistViewPagerAdapter
import dev.jaym21.cryptowatch.databinding.FragmentWatchlistBinding

class WatchlistFragment: Fragment() {

    private var binding: FragmentWatchlistBinding? = null
    private lateinit var watchlistViewPagerAdapter: WatchlistViewPagerAdapter
    var watchlists = arrayOf("Watchlist 1", "Watchlist 2", "Watchlist 3", "Watchlist 4")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing viewPager adapter
        watchlistViewPagerAdapter = WatchlistViewPagerAdapter(parentFragmentManager, lifecycle)
        //setting adapter to viewPager
        binding?.vpWatchlist?.adapter = watchlistViewPagerAdapter
        //integrating tablayout
        TabLayoutMediator(binding?.tlWatchlist!!, binding?.vpWatchlist!!) { tab, position ->
            tab.text = watchlists[position]
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}