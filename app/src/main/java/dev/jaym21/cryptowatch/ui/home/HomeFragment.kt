package dev.jaym21.cryptowatch.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.adapters.CurrencyRVAdapter
import dev.jaym21.cryptowatch.adapters.ICurrencyRVAdapter
import dev.jaym21.cryptowatch.databinding.FragmentHomeBinding
import dev.jaym21.cryptowatch.utils.ApiResponse

class HomeFragment : Fragment(), ICurrencyRVAdapter {

    private var binding: FragmentHomeBinding? = null
    private lateinit var navController: NavController
    private val currencyAdapter = CurrencyRVAdapter(this)
    private lateinit var viewModel: HomeViewModel
    private val TAG ="HomeFragment"
    private val PAGE_SIZE_QUERY = 20
    private var currentPage = 0
    private var itemsDisplayed = 0
    //pagination
    private var isScrolling: Boolean = false
    private var isLastPage: Boolean = false
    private var isLoading: Boolean = false

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

        //initializing navController
        navController = Navigation.findNavController(view)

        //initializing recyclerView
        setUpRecyclerView()

        //initializing viewModel
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        //calling function to get currencies
        currentPage = 0
        viewModel.getCurrencies(currentPage.toString(), "USD")


        //observing the currencies LiveData to get currencies data for recycler view
        viewModel.currencies.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is ApiResponse.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    currencyAdapter.submitList(response.data)
                    Log.d("TAGYOYO", "CURRENT PAGE $currentPage")
                    Log.d("TAGYOYO", "ITEMS DISPLAYED $itemsDisplayed")
                    //setting boolean isLastPage according to the current page no
                    isLastPage = itemsDisplayed + 20 >= response.data!!.size
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

    //implementing pagination
    private val paginationScrollListener = object :RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            //checking if the user is scrolling
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
             isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            //getting the linear layout manager from recycler view
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            //now getting the first item position which is visible on page
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            //getting the total visible items count on page
            val visibleItemCount = layoutManager.childCount

            //getting the total items count in recycler view
            val totalItemsCount = layoutManager.itemCount

            //checking if user is not at the last page or are not loading
            val isNotAtLastPageAndNotLoading = !isLastPage && !isLoading

            //checking if user is at the last page of the response
            val isAtLastPage = firstVisibleItemPosition + visibleItemCount >= totalItemsCount

            //checking if user has scrolled from the first page
            val notAtBeginning = firstVisibleItemPosition >= 0

            //checking if the no.of items in 1 query response are all loaded in recycler view
            val isTotalMoreThanVisible = totalItemsCount >= PAGE_SIZE_QUERY

            //creating a boolean to know if to paginate or not
            val shouldPaginate = isNotAtLastPageAndNotLoading && isAtLastPage && isTotalMoreThanVisible && notAtBeginning && isScrolling

            if (shouldPaginate) {
                currentPage += 1
                itemsDisplayed += 20
                Log.d("TAGYOYO", "onScrolled: CURRENT PAGE $currentPage")
                Log.d("TAGYOYO", "onScrolled: ITEMS DISPLAYED $itemsDisplayed")
                //makes another request to the api and gets next 20 products
                viewModel.getCurrencies(currentPage.toString(), "USD")
            }
        }
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

    override fun onCurrencyClicked(currencyId: String) {
        val bundle = bundleOf("currencyId" to currencyId, "convertTo" to "INR")
        navController.navigate(R.id.action_navigation_home_to_currencyDetailsFragment, bundle)
    }
}