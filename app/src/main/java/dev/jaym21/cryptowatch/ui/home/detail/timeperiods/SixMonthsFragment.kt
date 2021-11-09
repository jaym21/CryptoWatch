package dev.jaym21.cryptowatch.ui.home.detail.timeperiods

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
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

        //initializing viewModel
        viewModel = ViewModelProvider(this).get(CurrencyDetailsViewModel::class.java)

        //initializing markerView
        customMarkerView = CustomMarkerView(requireContext(), R.layout.chart_marker_view)

        //calling for hourly data of currency
        viewModel.getCurrencyDailyHistory(currencyId ,convertTo, "183")

        //styling chart
        binding?.chart?.axisLeft?.isEnabled = false
        binding?.chart?.axisRight?.isEnabled = false
        binding?.chart?.xAxis?.isEnabled = false
        binding?.chart?.legend?.isEnabled = false
        binding?.chart?.description?.isEnabled = false
        binding?.chart?.setTouchEnabled(true)
        binding?.chart?.isDragEnabled = true
        binding?.chart?.setScaleEnabled(false)
        binding?.chart?.setPinchZoom(false)
        binding?.chart?.marker = customMarkerView
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}