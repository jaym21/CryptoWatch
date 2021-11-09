package dev.jaym21.cryptowatch.ui.home.detail.timeperiods

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.data.Entry
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.databinding.FragmentOneDayBinding
import dev.jaym21.cryptowatch.ui.home.detail.CurrencyDetailsViewModel
import dev.jaym21.cryptowatch.utils.ApiResponse
import dev.jaym21.cryptowatch.utils.CustomMarkerView

class OneDayFragment(val currencyId: String, val convertTo: String) : Fragment() {

    private var binding: FragmentOneDayBinding? = null
    private lateinit var viewModel: CurrencyDetailsViewModel
    private lateinit var customMarkerView: CustomMarkerView
    private var entriesOneDay = arrayListOf<Entry>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOneDayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing viewModel
        viewModel = ViewModelProvider(this).get(CurrencyDetailsViewModel::class.java)

        //initializing markerView
        customMarkerView = CustomMarkerView(requireContext(), R.layout.chart_marker_view)

        //calling for hourly data of currency
        viewModel.getCurrencyHourlyHistory(currencyId ,convertTo)

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

        viewModel.currencyHourlyHistory.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is ApiResponse.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    entriesOneDay.clear()
                    response.data?.data?.forEach{
                        entriesOneDay.add(Entry(it.time!!.toFloat(), it.close!!.toFloat()))
                    }
                }
                is ApiResponse.Loading -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }
                is ApiResponse.Error -> {
                    binding?.progressBar?.visibility = View.GONE
                    Snackbar.make(view, "Could retrieve details, restart app!", Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }
}