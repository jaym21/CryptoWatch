package dev.jaym21.cryptowatch.ui.home.detail.timeperiods

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.databinding.FragmentSixMonthsBinding
import dev.jaym21.cryptowatch.ui.home.detail.CurrencyDetailsViewModel
import dev.jaym21.cryptowatch.utils.ApiResponse
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

        viewModel.currencyDailyHistory.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is ApiResponse.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    entriesSixMonths.clear()
                    response.data?.data?.forEach{
                        entriesSixMonths.add(Entry(it.time!!.toFloat(), it.close!!.toFloat()))
                    }
                    val dataSet = LineDataSet(entriesSixMonths, "line chart")
                    updateChart(dataSet)
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

    private fun updateChart(lineDataSet: LineDataSet) {
        lineDataSet.lineWidth = 2f
        lineDataSet.setDrawFilled(true)
        lineDataSet.setDrawHighlightIndicators(false)
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawIcons(false)
        lineDataSet.disableDashedLine()

        if (isChangePositive) {
            lineDataSet.fillDrawable = ContextCompat.getDrawable(binding!!.root.context, R.drawable.chart_fade_green)
            lineDataSet.color = ContextCompat.getColor(
                binding!!.root.context,
                R.color.green
            )
        } else {
            lineDataSet.fillDrawable = ContextCompat.getDrawable(binding!!.root.context, R.drawable.chart_fade_red)
            lineDataSet.color = ContextCompat.getColor(
                binding!!.root.context,
                R.color.red
            )
        }

        val data = LineData(lineDataSet)

        data.setDrawValues(false)

        binding?.chart?.data = data
        binding?.chart?.animateXY(2000, 2000, Easing.EaseInCubic)
        binding?.chart?.invalidate()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}