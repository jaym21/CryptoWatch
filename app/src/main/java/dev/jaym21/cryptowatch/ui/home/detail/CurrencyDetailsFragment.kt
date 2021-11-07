package dev.jaym21.cryptowatch.ui.home.detail

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.snackbar.Snackbar
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.databinding.FragmentCurrencyDetailsBinding
import dev.jaym21.cryptowatch.utils.ApiResponse
import dev.jaym21.cryptowatch.utils.SVGLoader

class CurrencyDetailsFragment : Fragment(), OnChartValueSelectedListener {

    private var binding: FragmentCurrencyDetailsBinding? = null
    private var TAG = "CurrencyDetailsFragment"
    private lateinit var viewModel: CurrencyDetailsViewModel
    private var currencyId: String? = null
    private var convertTo: String? = null
    private lateinit var navController: NavController
    private var entries = arrayListOf<Entry>()
    private var isChangePositive: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrencyDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing navController
        navController = Navigation.findNavController(view)

        //getting clicked currency id
        currencyId = arguments?.getString("currencyId")
        //currency to be converted to
        convertTo = arguments?.getString("convertTo")

        if (currencyId == null) {
            Snackbar.make(view, "Could not find currency, try again!", Snackbar.LENGTH_SHORT).show()
        }

        binding?.ivBackButton?.setOnClickListener {
            navController.popBackStack()
        }

        viewModel = ViewModelProvider(this).get(CurrencyDetailsViewModel::class.java)

        viewModel.getCurrencyDetails(currencyId!!, convertTo!!)

        binding?.tvConvertedToCurrency?.text = convertTo

        viewModel.currencyDetails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    binding?.tvCurrencyName?.text = response.data!![0].name
                    binding?.tvCurrentPrice?.text = response.data[0].price

                    val length = response.data[0].logoUrl!!.length
                    if (response.data[0].logoUrl!!.substring(length - 3) == "svg"){
                        SVGLoader.fetchSvg(binding!!.root.context, response.data[0].logoUrl!!, binding!!.ivCurrencyIcon)
                    }else {
                        Glide.with(binding!!.root.context).load(response.data[0].logoUrl).transform(
                            RoundedCorners(100)
                        ) .into(binding!!.ivCurrencyIcon)
                    }

                    if (response.data[0].oneDay != null) {

                        if (response.data[0].oneDay!!.priceChangePct!!.toDouble() >= 0) {
                            binding!!.tvPercentChange.text = response.data[0].oneDay!!.priceChangePct!! + " %"

                            Glide.with(binding!!.root.context).load(R.drawable.ic_trending_up).into(binding!!.ivChangeIcon)
                            binding!!.ivChangeIcon.setColorFilter(ContextCompat.getColor(binding!!.root.context, R.color.green))
                            binding!!.tvPercentChange.setTextColor(
                                ContextCompat.getColor(
                                    binding!!.root.context,
                                    R.color.green
                                )
                            )
                            binding!!.cvPriceChange.background =ContextCompat.getDrawable(binding!!.root.context, R.drawable.positive_change_card_bg)
                            isChangePositive = true
                        } else {
                            binding!!.tvPercentChange.text = response.data[0].oneDay!!.priceChangePct!!.substring(1) + " %"

                            Glide.with(binding!!.root.context).load(R.drawable.ic_trending_down).into(binding!!.ivChangeIcon)
                            binding!!.ivChangeIcon.setColorFilter(ContextCompat.getColor(binding!!.root.context, R.color.red))
                            binding!!.tvPercentChange.setTextColor(
                                ContextCompat.getColor(
                                    binding!!.root.context,
                                    R.color.red
                                )
                            )
                            binding!!.cvPriceChange.background =ContextCompat.getDrawable(binding!!.root.context, R.drawable.negative_change_card_bg)
                            isChangePositive = false
                        }
                    }

                    //line chart
                    // disable description text
                    binding?.chart?.description?.isEnabled = false

                    // enable touch gestures
                    binding?.chart?.setTouchEnabled(true)

                    binding?.chart?.setOnChartValueSelectedListener(this)
                    binding?.chart?.setDrawGridBackground(false)

                    viewModel.getCurrencyHistory(currencyId!!, "USD", "30")

                    viewModel.currencyHistory.observe(viewLifecycleOwner, Observer { response ->
                        when(response) {
                            is ApiResponse.Success -> {
                                response.data?.data?.forEach {
                                    entries.add(Entry(it.time!!.toFloat(), it.high!!.toFloat()))
                                }
                                val dataSet = LineDataSet(entries, "1 month")
                                updateChart(dataSet)
                            }
                        }
                    })
                }

                is ApiResponse.Loading -> {
                    binding?.progressBar?.visibility = View.VISIBLE
                }

                is ApiResponse.Error -> {
                    binding?.progressBar?.visibility = View.GONE
                    Snackbar.make(view, "Could retrieve details, restart app!", Snackbar.LENGTH_SHORT).show()
                    navController.popBackStack()
                }
            }
        })
    }

    private fun updateChart(lineDataSet: LineDataSet) {
        if (binding?.chart?.data != null && binding?.chart?.data?.dataSetCount!! > 0) {
            val set = binding?.chart?.data!!.getDataSetByIndex(0) as LineDataSet
            set.values = lineDataSet.values
            binding?.chart?.data!!.notifyDataChanged()
            binding?.chart?.notifyDataSetChanged()
        } else {
            val dataSet = LineDataSet(entries, "1 month")
            dataSet.setDrawIcons(false)
            dataSet.enableDashedLine(10f, 5f, 0f)
            dataSet.enableDashedHighlightLine(10f, 5f, 0f)
            dataSet.color = Color.WHITE
            dataSet.setCircleColor(Color.WHITE)
            dataSet.lineWidth = 1f
            dataSet.circleRadius = 3f
            dataSet.setDrawCircleHole(false)
            dataSet.valueTextSize = 9f
            dataSet.setDrawFilled(true)
            dataSet.formLineWidth = 1f
            dataSet.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            dataSet.formSize = 15f
            if (isChangePositive)
                dataSet.fillColor = R.drawable.chart_fade_green
            else
                dataSet.fillColor = R.drawable.chart_fade_red
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.d(TAG, "onValueSelected: ENTRY SELECTED ${e.toString()}")
    }

    override fun onNothingSelected() {
        Log.d(TAG, "NOTHING SELECTED")
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}