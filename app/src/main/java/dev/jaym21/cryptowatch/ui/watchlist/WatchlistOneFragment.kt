package dev.jaym21.cryptowatch.ui.watchlist

import android.graphics.Color
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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.cryptoapi.models.responses.CurrencyResponse
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.adapters.CurrencyRVAdapter
import dev.jaym21.cryptowatch.adapters.ICurrencyRVAdapter
import dev.jaym21.cryptowatch.databinding.FragmentWatchlistOneBinding
import dev.jaym21.cryptowatch.model.Watchlist
import dev.jaym21.cryptowatch.ui.home.detail.CurrencyDetailsFragment
import dev.jaym21.cryptowatch.utils.ApiResponse
import dev.jaym21.cryptowatch.utils.SwipeToDeleteCallback

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


        //initializing watchlist viewModel
        watchlistViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(WatchlistViewModel::class.java)

        //initializing adapter
        currencyAdapter = CurrencyRVAdapter(this)

        //initializing recyclerView
        setUpRecyclerView()

        //setting swipe to delete item
        setUpSwipeToDeleteItem()

        watchlistViewModel.allCurrenciesInWatchlist.observe(viewLifecycleOwner, Observer { allWatchlists ->
            var requiredCurrencies = ""
            allWatchlists.forEach {
                if (it.watchlist == "Watchlist 1") {
                    if (requiredCurrencies.isEmpty()) {
                        requiredCurrencies = it.symbol
                    } else {
                        requiredCurrencies += ", ${it.symbol}"
                    }
                }
            }
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

    private fun setUpSwipeToDeleteItem() {
        val swipeToDeleteCallback = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                //getting position of item which is swiped
                val itemPosition = viewHolder.adapterPosition
                //getting currentList
                val currentList =  currencyAdapter.currentList.toMutableList()
                //getting the swiped item
                val swipedItem = currentList[itemPosition]
                //removing item from list
                currentList.removeAt(itemPosition)

                //removing from database
                val requiredWatchlist = getWatchlistObjectFromCurrencyResponse(swipedItem)
                if (requiredWatchlist != null) {
                    watchlistViewModel.removeCurrencyFromWatchlist(requiredWatchlist)
                    //updating recycler view
                    currencyAdapter.submitList(currentList)
                    //showing a snackbar with undo option
                    val snackbar = Snackbar.make(binding?.root!!, "Currency removed from watchlist", Snackbar.LENGTH_LONG)
                    snackbar.setAction("UNDO") {
                        val newCurrentList  = currencyAdapter.currentList.toMutableList()
                        newCurrentList.add(itemPosition, swipedItem)

                        //adding item back to database
                        watchlistViewModel.addCurrencyToWatchlist(requiredWatchlist)
                        //updating recycler view
                        currencyAdapter.submitList(newCurrentList)
                    }
                    snackbar.setActionTextColor(Color.YELLOW)
                    snackbar.show()
                }else {
                    Snackbar.make(binding?.root!!, "Could not delete currency, restart app and try again!", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding?.rvWatchlistOne)
    }

    private fun getWatchlistObjectFromCurrencyResponse(currencyResponse: CurrencyResponse): Watchlist? {
        var watchlist: Watchlist ?= null
        watchlistViewModel.allCurrenciesInWatchlist.observe(viewLifecycleOwner, Observer {  allWatchlists ->
            allWatchlists.forEach {
                if (it.symbol == currencyResponse.symbol && it.watchlist == "Watchlist 1") {
                    watchlist = it
                    return@Observer
                }
            }
        })
        return watchlist
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    override fun onCurrencyClicked(currencyId: String, isChangePositive: Boolean) {
        val bundle = bundleOf("currencyId" to currencyId, "isChangePositive" to isChangePositive)
        navController.navigate(R.id.action_navigation_watchlist_to_currencyDetailsFragment, bundle)
//        val fragmentManager = requireActivity().supportFragmentManager
//        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.rlWatchlistRoot, CurrencyDetailsFragment())
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
    }
}