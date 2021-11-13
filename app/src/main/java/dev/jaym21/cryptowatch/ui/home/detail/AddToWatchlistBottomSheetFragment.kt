package dev.jaym21.cryptowatch.ui.home.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.jaym21.cryptowatch.databinding.FragmentAddToWatchlistBottomSheetBinding

class AddToWatchlistBottomSheetFragment : BottomSheetDialogFragment() {

    private var binding: FragmentAddToWatchlistBottomSheetBinding? = null
    private lateinit var currencyName: String
    private lateinit var currencyId: String

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

        //getting currency name and id from passed arguments
        currencyName = arguments?.getString("currencyName")!!
        currencyId = arguments?.getString("currencySymbol")!!

        //setting currency name in text
        binding?.tvChooseWatchlist?.text = "Choose watchlist to add $currencyName"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}