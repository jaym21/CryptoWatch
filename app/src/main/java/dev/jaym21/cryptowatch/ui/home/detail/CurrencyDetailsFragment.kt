package dev.jaym21.cryptowatch.ui.home.detail

import android.os.Bundle
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
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.adapters.ViewPagerAdapter
import dev.jaym21.cryptowatch.databinding.FragmentCurrencyDetailsBinding
import dev.jaym21.cryptowatch.utils.ApiResponse
import dev.jaym21.cryptowatch.utils.CustomMarkerView
import dev.jaym21.cryptowatch.utils.SVGLoader

class CurrencyDetailsFragment : Fragment() {

    private var binding: FragmentCurrencyDetailsBinding? = null
    private var TAG = "CurrencyDetailsFragment"
    private lateinit var viewModel: CurrencyDetailsViewModel
    private var currencyId: String? = null
    private var convertTo: String? = null
    private lateinit var navController: NavController
    private var entries = arrayListOf<Entry>()
    private var isChangePositive: Boolean = false
    private lateinit var customMarkerView: CustomMarkerView
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    val timePeriods = arrayOf("1d", "7d", "30d", "6m", "1y")

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

        //initializing viewModel
        viewModel = ViewModelProvider(this).get(CurrencyDetailsViewModel::class.java)

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

        //initializing markerView
        customMarkerView = CustomMarkerView(requireContext(), R.layout.chart_marker_view)
//
//        binding?.btnOneDay?.setOnClickListener(this)
//        binding?.btnSevenDays?.setOnClickListener(this)
//        binding?.btnOneMonth?.setOnClickListener(this)
//        binding?.btnSixMonths?.setOnClickListener(this)
//        binding?.btnOneYear?.setOnClickListener(this)
//
//        binding?.btnOneDay?.performClick()

        viewModel.getCurrencyDetails(currencyId!!, convertTo!!)

        binding?.tvConvertedToCurrency?.text = convertTo

        viewModel.currencyDetails.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is ApiResponse.Success -> {
                    binding?.progressBar?.visibility = View.GONE
                    binding?.tvCurrencyName?.text = response.data!![0].name
                    binding?.tvCurrentPrice?.text = response.data[0].price

                    val length = response.data[0].logoUrl!!.length
                    if (response.data[0].logoUrl!!.substring(length - 3) == "svg") {
                        SVGLoader.fetchSvg(
                            binding!!.root.context,
                            response.data[0].logoUrl!!,
                            binding!!.ivCurrencyIcon
                        )
                    } else {
                        Glide.with(binding!!.root.context).load(response.data[0].logoUrl).transform(
                            RoundedCorners(100)
                        ).into(binding!!.ivCurrencyIcon)
                    }

                    if (response.data[0].oneDay != null) {

                        if (response.data[0].oneDay!!.priceChangePct!!.toDouble() >= 0) {
                            binding!!.tvPercentChange.text =
                                response.data[0].oneDay!!.priceChangePct!! + " %"

                            Glide.with(binding!!.root.context).load(R.drawable.ic_trending_up)
                                .into(binding!!.ivChangeIcon)
                            binding!!.ivChangeIcon.setColorFilter(
                                ContextCompat.getColor(
                                    binding!!.root.context,
                                    R.color.green
                                )
                            )
                            binding!!.tvPercentChange.setTextColor(
                                ContextCompat.getColor(
                                    binding!!.root.context,
                                    R.color.green
                                )
                            )
                            binding!!.cvPriceChange.background = ContextCompat.getDrawable(
                                binding!!.root.context,
                                R.drawable.positive_change_card_bg
                            )
                            isChangePositive = true
                        } else {
                            binding!!.tvPercentChange.text =
                                response.data[0].oneDay!!.priceChangePct!!.substring(1) + " %"

                            Glide.with(binding!!.root.context).load(R.drawable.ic_trending_down)
                                .into(binding!!.ivChangeIcon)
                            binding!!.ivChangeIcon.setColorFilter(
                                ContextCompat.getColor(
                                    binding!!.root.context,
                                    R.color.red
                                )
                            )
                            binding!!.tvPercentChange.setTextColor(
                                ContextCompat.getColor(
                                    binding!!.root.context,
                                    R.color.red
                                )
                            )
                            binding!!.cvPriceChange.background = ContextCompat.getDrawable(
                                binding!!.root.context,
                                R.drawable.negative_change_card_bg
                            )
                            isChangePositive = false
                        }
                    }

                }
                    //hourly historical data for line chart
//                    viewModel.getCurrencyHourlyHistory(currencyId!!, convertTo!!)
//
//                    viewModel.currencyDailyHistory.observe(viewLifecycleOwner, Observer { response ->
//                        when(response) {
//                            is ApiResponse.Success -> {
//                                entries.clear()
//                                response.data?.data?.forEach {
//                                    entries.add(Entry(it.time!!.toFloat(), it.high!!.toFloat()))
//                                }
//                                val dataSet = LineDataSet(entries, "line chart")
//                                updateChart(dataSet)
//                            }
//                        }
//                    })
//                }
//
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


        //initializing viewPagerAdapter
        viewPagerAdapter = ViewPagerAdapter(parentFragmentManager, lifecycle, currencyId!!, convertTo!!)

        binding?.viewPager?.adapter = viewPagerAdapter

        TabLayoutMediator(binding?.tabLayout!!, binding?.viewPager!!) { tab, position ->
            tab.text = timePeriods[position].lowercase()
        }.attach()

        for (i in 0 until binding?.tabLayout!!.tabCount) {
            val tab  = (binding?.tabLayout?.getChildAt(0) as ViewGroup).getChildAt(i)
            val params = tab.layoutParams as ViewGroup.MarginLayoutParams
            params.setMargins(0,0,20,0)
            tab.requestLayout()
        }
    }

//    private fun updateChart(lineDataSet: LineDataSet) {
//        binding?.chart?.axisLeft?.isEnabled = false
//        binding?.chart?.axisRight?.isEnabled = false
//        binding?.chart?.xAxis?.isEnabled = false
//        binding?.chart?.legend?.isEnabled = false
//        binding?.chart?.description?.isEnabled = false
//        binding?.chart?.setTouchEnabled(true)
//        binding?.chart?.isDragEnabled = true
//        binding?.chart?.setScaleEnabled(false)
//        binding?.chart?.setPinchZoom(false)
//        binding?.chart?.marker = customMarkerView
//
//        lineDataSet.lineWidth = 2f
//        lineDataSet.setDrawFilled(true)
//        lineDataSet.setDrawHighlightIndicators(false)
//        lineDataSet.setDrawCircleHole(false)
//        lineDataSet.setDrawCircles(false)
//        lineDataSet.setDrawValues(false)
//        lineDataSet.setDrawIcons(false)
//        lineDataSet.disableDashedLine()
//
//        if (isChangePositive) {
//            lineDataSet.fillDrawable = ContextCompat.getDrawable(binding!!.root.context, R.drawable.chart_fade_green)
//            lineDataSet.color = ContextCompat.getColor(
//                binding!!.root.context,
//                R.color.green
//            )
//        } else {
//            lineDataSet.fillDrawable = ContextCompat.getDrawable(binding!!.root.context, R.drawable.chart_fade_red)
//            lineDataSet.color = ContextCompat.getColor(
//                binding!!.root.context,
//                R.color.red
//            )
//        }
//
//        val data = LineData(lineDataSet)
//
//        data.setDrawValues(false)
//
//        binding?.chart?.data = data
//        binding?.chart?.animateXY(3000, 3000, Easing.EaseInCubic)
//        binding?.chart?.invalidate()
//    }

//    override fun onResume() {
//        super.onResume()
//        binding?.chart?.invalidate()
//        binding?.chart?.notifyDataSetChanged()
//    }

//    override fun onClick(view: View?) {
//        when(view?.id) {
//            R.id.btnOneDay -> {
//                //selected
//                binding?.btnOneDay?.setTextColor(resources.getColor(R.color.white, requireActivity().theme))
//                binding?.btnOneDay?.setBackgroundColor(resources.getColor(R.color.blue_700, requireActivity().theme))
//                //not selected
//                binding?.btnSevenDays?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnSevenDays?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnOneMonth?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneMonth?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnSixMonths?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnSixMonths?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnOneYear?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneYear?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//
//                //making api call for hourly historical data
//                viewModel.getCurrencyHourlyHistory(currencyId!!, convertTo!!)
//                binding?.chart?.invalidate()
//            }
//
//            R.id.btnSevenDays -> {
//                //selected
//                binding?.btnSevenDays?.setTextColor(resources.getColor(R.color.white, requireActivity().theme))
//                binding?.btnSevenDays?.setBackgroundColor(resources.getColor(R.color.blue_700, requireActivity().theme))
//                //not selected
//                binding?.btnOneDay?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneDay?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnOneMonth?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneMonth?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnSixMonths?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnSixMonths?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnOneYear?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneYear?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//
//                //making api call for daily historical data for seven days
//                viewModel.getCurrencyDailyHistory(currencyId!!, convertTo!!, "7")
//                binding?.chart?.invalidate()
//            }
//
//            R.id.btnOneMonth -> {
//                //selected
//                binding?.btnOneMonth?.setTextColor(resources.getColor(R.color.white, requireActivity().theme))
//                binding?.btnOneMonth?.setBackgroundColor(resources.getColor(R.color.blue_700, requireActivity().theme))
//                //not selected
//                binding?.btnOneDay?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneDay?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnSevenDays?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnSevenDays?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnSixMonths?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnSixMonths?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnOneYear?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneYear?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//
//                //making api call for daily historical data for one month
//                viewModel.getCurrencyDailyHistory(currencyId!!, convertTo!!, "30")
//                binding?.chart?.invalidate()
//            }
//
//            R.id.btnSixMonths -> {
//                //selected
//                binding?.btnSixMonths?.setTextColor(resources.getColor(R.color.white, requireActivity().theme))
//                binding?.btnSixMonths?.setBackgroundColor(resources.getColor(R.color.blue_700, requireActivity().theme))
//                //not selected
//                binding?.btnOneDay?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneDay?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnSevenDays?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnSevenDays?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnOneMonth?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneMonth?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnOneYear?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneYear?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//
//                //making api call for daily historical data for six days
//                viewModel.getCurrencyDailyHistory(currencyId!!, convertTo!!, "183")
//                binding?.chart?.invalidate()
//            }
//
//            R.id.btnOneYear -> {
//                //selected
//                binding?.btnOneYear?.setTextColor(resources.getColor(R.color.white, requireActivity().theme))
//                binding?.btnOneYear?.setBackgroundColor(resources.getColor(R.color.blue_700, requireActivity().theme))
//                //not selected
//                binding?.btnOneDay?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneDay?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnSevenDays?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnSevenDays?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnOneMonth?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnOneMonth?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//                binding?.btnSixMonths?.setTextColor(resources.getColor(R.color.white_alpha_20, requireActivity().theme))
//                binding?.btnSixMonths?.setBackgroundColor(resources.getColor(R.color.black_alpha_20, requireActivity().theme))
//
//                //making api call for daily historical data for one year
//                viewModel.getCurrencyDailyHistory(currencyId!!, convertTo!!, "365")
//                binding?.chart?.invalidate()
//            }
//        }
//    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}