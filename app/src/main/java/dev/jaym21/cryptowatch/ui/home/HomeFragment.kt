package dev.jaym21.cryptowatch.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.cryptowatch.adapters.CurrencyRVAdapter
import dev.jaym21.cryptowatch.databinding.FragmentHomeBinding
import dev.jaym21.cryptowatch.utils.ApiResponse

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private val currencyAdapter = CurrencyRVAdapter()
    private lateinit var viewModel: HomeViewModel
    private val TAG ="HomeFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing recyclerView
        setUpRecyclerView()

        //initializing viewModel
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        //calling function to get currencies
        viewModel.getCurrencies()


        //observing the currencies LiveData to get currencies data for recycler view
        viewModel.currencies.observe(viewLifecycleOwner, Observer { response ->
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
                    Snackbar.make(view, "Could retrieve currencies, restart app!", Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setUpRecyclerView() {
        binding?.rvCurrencies?.apply {
            adapter = currencyAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}