package dev.jaym21.cryptowatch.ui.home.detail.timeperiods

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.data.Entry
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.databinding.FragmentSixMonthsBinding
import dev.jaym21.cryptowatch.ui.home.detail.CurrencyDetailsViewModel
import dev.jaym21.cryptowatch.utils.CustomMarkerView


class SixMonthsFragment(val currencyId: String, val convertTo: String, val isChangePositive: Boolean) : Fragment() {

    private var binding: FragmentSixMonthsBinding? = null
    private lateinit var viewModel: CurrencyDetailsViewModel
    private lateinit var customMarkerView: CustomMarkerView
    private var entriesSixMonths = arrayListOf<Entry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSixMonthsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}