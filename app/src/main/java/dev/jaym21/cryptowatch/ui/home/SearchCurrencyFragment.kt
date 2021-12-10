package dev.jaym21.cryptowatch.ui.home

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
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.cryptoapi.models.responses.CurrencyResponse
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.adapters.CurrencyRVAdapter
import dev.jaym21.cryptowatch.adapters.ICurrencyRVAdapter
import dev.jaym21.cryptowatch.databinding.FragmentSearchCurrencyBinding
import dev.jaym21.cryptowatch.utils.ApiResponse

class SearchCurrencyFragment : Fragment(), ICurrencyRVAdapter {

    private var binding: FragmentSearchCurrencyBinding? = null
    private lateinit var navController: NavController
    private lateinit var currencyAdapter: CurrencyRVAdapter
    private lateinit var viewModel: HomeViewModel
    private var searchedText: String? = null
    private var searchedCurrencies = arrayListOf<CurrencyResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchCurrencyBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("TAGYOYO", "SEARCH ONVIEWCREATED CALLED")

        //getting searched text from home fragment
        searchedText = arguments?.getString("searchedText")

        //initializing navController
        navController = Navigation.findNavController(view)

        if (searchedText != null) {

            Log.d("TAGYOYO", "SEARCHED TEXT $searchedText")

            //initializing viewModel
            viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

            //initializing adapter
            currencyAdapter = CurrencyRVAdapter(this)

            //initializing recyclerView
            setUpRecyclerView()

            //making request to get all currencies
            viewModel.getAllCurrencies()

            viewModel.allCurrencies.observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        searchedCurrencies.clear()
                        response.data?.forEach {
                            if (it.name?.contains(searchedText.toString(), true) == true) {
                                searchedCurrencies.add(it)
                            }
                        }
                        currencyAdapter.submitList(searchedCurrencies)
                    }
                    is ApiResponse.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is ApiResponse.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Snackbar.make(
                            binding?.root!!,
                            "Could retrieve currencies, restart app!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        } else {

        }
    }

    private fun setUpRecyclerView() {
        binding?.rvSearchedCurrencies?.apply {
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
        navController.navigate(R.id.action_navigation_home_to_currencyDetailsFragment, bundle)
    }
}