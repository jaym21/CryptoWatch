package dev.jaym21.cryptowatch.ui.home.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.cryptowatch.databinding.FragmentAddToWatchlistBottomSheetBinding
import dev.jaym21.cryptowatch.model.Watchlist
import dev.jaym21.cryptowatch.ui.watchlist.WatchlistViewModel

class AddToWatchlistBottomSheetFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddToWatchlistBottomSheetBinding? = null
    private lateinit var currencyName: String
    private lateinit var currencyId: String
    lateinit var watchlistViewModel: WatchlistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddToWatchlistBottomSheetBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing watchlist viewModel
        watchlistViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(WatchlistViewModel::class.java)

        //getting currency name and id from passed arguments
        currencyName = arguments?.getString("currencyName")!!
        currencyId = arguments?.getString("currencySymbol")!!

        //setting currency name in text
        binding?.tvChooseWatchlist?.text = "Choose watchlist to add $currencyName"

        binding?.btnAddToWatchlist?.setOnClickListener {
            //getting the checked button id
            val selectedId = binding?.rgWatchlist?.checkedRadioButtonId
            val selectedWatchlist = view.findViewById<RadioButton>(selectedId!!)

            //checking if already present in database
            var isPresent = false
            watchlistViewModel.allCurrenciesInWatchlist.observe(viewLifecycleOwner, Observer { allWatchlists ->
                allWatchlists.forEach {
                    if (it.symbol == currencyId && it.watchlist == selectedWatchlist.text.toString()) {
                        isPresent = true
                    }
                }
                if (isPresent) {
                    Toast.makeText(requireContext(), "$currencyName is already present in ${selectedWatchlist.text}", Toast.LENGTH_SHORT).show()
                    dismiss()
                } else {
                    addToDatabase(selectedWatchlist.text.toString())
                }
            })
        }
    }

    private fun addToDatabase(selectedWatchlist: String) {
        //adding currency to selected watchlist in database
        watchlistViewModel.addCurrencyToWatchlist(
            Watchlist(
                0,
                currencyId,
                selectedWatchlist
            )
        )
        Toast.makeText(requireContext(), "$currencyName added to $selectedWatchlist", Toast.LENGTH_SHORT).show()
        dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}