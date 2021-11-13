package dev.jaym21.cryptowatch.ui.watchlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.adapters.CurrencyRVAdapter
import dev.jaym21.cryptowatch.adapters.ICurrencyRVAdapter
import dev.jaym21.cryptowatch.databinding.FragmentWatchlistOneBinding
import dev.jaym21.cryptowatch.utils.ApiResponse

class WatchlistOneFragment(private val navController: NavController): Fragment(), ICurrencyRVAdapter {

    private var binding: FragmentWatchlistOneBinding? = null
    lateinit var watchlistViewModel: WatchlistViewModel
    private lateinit var currencyAdapter: CurrencyRVAdapter

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

        //TODO: crash on back press

        //initializing watchlist viewModel
        watchlistViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(WatchlistViewModel::class.java)

        //initializing adapter
        currencyAdapter = CurrencyRVAdapter(this)

        //initializing recyclerView
        setUpRecyclerView()

        watchlistViewModel.allCurrenciesInWatchlist.observe(viewLifecycleOwner, Observer { allWatchlists ->
            var requiredCurrencies = ""
            Log.d("TAGYOYO", "ALL Watchlists $allWatchlists")
            allWatchlists.forEach {
                if (it.watchlist == "Watchlist 1") {
                    if (requiredCurrencies.isEmpty()) {
                        requiredCurrencies = it.symbol
                        Log.d("TAGYOYO", "FIRST INSIDE IF ${it.symbol} REQCUR $requiredCurrencies")
                    } else {
                        requiredCurrencies += ", ${it.symbol}"
                        Log.d("TAGYOYO", "${it.symbol} REQCUR $requiredCurrencies")
                    }
                }
            }
            Log.d("TAGYOYO", "OUTSIDE IF REQCUR $requiredCurrencies")
            if (requiredCurrencies.isNotEmpty()) {
                binding?.llNoCurrenciesFound?.visibility = View.GONE
                binding?.rvWatchlistOne?.visibility = View.VISIBLE
                watchlistViewModel.fetchCurrencies(requiredCurrencies)
            } else {
                binding?.llNoCurrenciesFound?.visibility = View.VISIBLE
                binding?.rvWatchlistOne?.visibility = View.GONE
            }
        })

        watchlistViewModel.requiredCurrencies.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is ApiResponse.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    currencyAdapter.submitList(response.data)
                    Log.d("TAGYOYO", "API RESPONSE SUCCESS ${response.data}")
                }
                is ApiResponse.Loading -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                is ApiResponse.Error -> {
                    binding?.progressBar?.visibility = View.GONE
                    Snackbar.make(
                        view,
                        "Could retrieve currencies, restart app!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setUpRecyclerView() {
        binding?.rvWatchlistOne?.apply {
            adapter = currencyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onCurrencyClicked(currencyId: String, isChangePositive: Boolean) {
        val bundle = bundleOf("currencyId" to currencyId, "isChangePositive" to isChangePositive)
        navController.navigate(R.id.action_navigation_watchlist_to_currencyDetailsFragment, bundle)
    }
}