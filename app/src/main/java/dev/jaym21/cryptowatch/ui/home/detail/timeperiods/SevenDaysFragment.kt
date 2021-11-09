package dev.jaym21.cryptowatch.ui.home.detail.timeperiods

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.Entry
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.databinding.FragmentSevenDaysBinding
import dev.jaym21.cryptowatch.ui.home.detail.CurrencyDetailsViewModel
import dev.jaym21.cryptowatch.utils.CustomMarkerView

class SevenDaysFragment(val currencyId: String, val convertTo: String, val isChangePositive: Boolean) : Fragment() {

    private var binding: FragmentSevenDaysBinding? = null
    private lateinit var viewModel: CurrencyDetailsViewModel
    private lateinit var customMarkerView: CustomMarkerView
    private var entriesSevenDays = arrayListOf<Entry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSevenDaysBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing viewModel
        viewModel = ViewModelProvider(this).get(CurrencyDetailsViewModel::class.java)

        //initializing markerView
        customMarkerView = CustomMarkerView(requireContext(), R.layout.chart_marker_view)

        //calling for hourly data of currency
        viewModel.getCurrencyDailyHistory(currencyId ,convertTo, "7")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}