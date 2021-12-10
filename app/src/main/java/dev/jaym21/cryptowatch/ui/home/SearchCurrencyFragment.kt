package dev.jaym21.cryptowatch.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.adapters.CurrencyRVAdapter
import dev.jaym21.cryptowatch.databinding.FragmentSearchCurrencyBinding

class SearchCurrencyFragment : Fragment() {

    private var binding: FragmentSearchCurrencyBinding? = null
    private lateinit var currencyAdapter: CurrencyRVAdapter
    private lateinit var viewModel: HomeViewModel

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

        //initializing viewModel
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun setUpRecyclerView() {
        binding?.rvSearchedCurrencies?.apply {
            adapter =
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}