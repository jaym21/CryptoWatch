package dev.jaym21.cryptowatch.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.databinding.FragmentWatchlistBinding

class WatchlistFragment : Fragment() {

    private var binding: FragmentWatchlistBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}