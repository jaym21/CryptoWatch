package dev.jaym21.cryptowatch.ui.home.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
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

        // TODO: handle already present currency getting added to watchlist
        //TODO: Snackbar not working

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
            //adding currency to selected watchlist in database
            watchlistViewModel.addCurrencyToWatchlist(Watchlist(0, currencyId, selectedWatchlist.text.toString()))
//            Snackbar.make(it, "$currencyName added to ${selectedWatchlist.text}", Snackbar.LENGTH_SHORT).show()
            Toast.makeText(requireContext(), "$currencyName added to ${selectedWatchlist.text}", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}