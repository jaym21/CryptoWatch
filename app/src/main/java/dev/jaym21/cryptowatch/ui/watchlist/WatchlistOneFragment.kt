package dev.jaym21.cryptowatch.ui.watchlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.databinding.FragmentWatchlistOneBinding
import dev.jaym21.cryptowatch.utils.ApiResponse

class WatchlistOneFragment : Fragment() {

    private var binding: FragmentWatchlistOneBinding? = null
    lateinit var watchlistViewModel: WatchlistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWatchlistOneBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing watchlist viewModel
        watchlistViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(WatchlistViewModel::class.java)

        watchlistViewModel.allCurrenciesInWatchlist.observe(viewLifecycleOwner, Observer { allWatchlists ->
            var requiredCurrencies = ""
            allWatchlists.forEach {
                if (it.watchlistNumber == "1") {
                    if (requiredCurrencies.isEmpty()) {
                        requiredCurrencies = it.symbol
                    } else {
                        requiredCurrencies += ", ${it.symbol}"
                    }
                }
            }
            if (requiredCurrencies.isNotEmpty())
                watchlistViewModel.fetchCurrencies(requiredCurrencies)
        })

        watchlistViewModel.requiredCurrencies.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is ApiResponse.Success ->
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}