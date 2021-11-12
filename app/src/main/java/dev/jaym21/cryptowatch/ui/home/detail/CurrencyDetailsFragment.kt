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
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.adapters.ChartViewPagerAdapter
import dev.jaym21.cryptowatch.databinding.FragmentCurrencyDetailsBinding
import dev.jaym21.cryptowatch.utils.ApiResponse
import dev.jaym21.cryptowatch.utils.SVGLoader

class CurrencyDetailsFragment : Fragment() {

    private var binding: FragmentCurrencyDetailsBinding? = null
    private var TAG = "CurrencyDetailsFragment"
    private lateinit var viewModel: CurrencyDetailsViewModel
    private var currencyId: String? = null
    private var convertTo: String? = null
    private var isChangePositive: Boolean? = null
    private lateinit var navController: NavController
    private lateinit var chartViewPagerAdapter: ChartViewPagerAdapter
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
        //getting isChangePositive
        isChangePositive = arguments?.getBoolean("isChangePositive")

        if (currencyId == null) {
            Snackbar.make(view, "Could not find currency, try again!", Snackbar.LENGTH_SHORT).show()
        }

        binding?.ivBackButton?.setOnClickListener {
            navController.popBackStack()
        }

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

                    //price change
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
                        }
                    }
                    //market stats
                    if (response.data[0].marketCap == null){
                        binding?.llMarketCap?.visibility = View.GONE
                        binding?.viewBelowMarketCap?.visibility = View.GONE
                    } else {
                        binding?.tvMarketCap?.text = "₹ ${response.data[0].marketCap}"
                    }
                    if (response.data[0].circulatingSupply == null){
                        binding?.llCirculatingSupply?.visibility = View.GONE
                        binding?.viewBelowCirculatingSupply?.visibility = View.GONE
                    } else {
                         binding?.tvCirculatingSupply?.text = response.data[0].circulatingSupply
                    }
                    if (response.data[0].maxSupply == null){
                        binding?.llMaxSupply?.visibility = View.GONE
                        binding?.viewBelowMaxSupply?.visibility = View.GONE
                    } else {
                        binding?.tvMaxSupply?.text = response.data[0].maxSupply
                    }
                    if (response.data[0].oneDay?.priceChange == null){
                        binding?.llPriceChange?.visibility = View.GONE
                        binding?.viewBelowPriceChange?.visibility = View.GONE
                    } else {
                        binding?.tvPriceChange?.text = "₹ ${response.data[0].oneDay?.priceChange}"
                    }
                    if (response.data[0].oneDay?.volumeChange == null){
                        binding?.llVolumeChange?.visibility = View.GONE
                    } else {
                        binding?.tvVolumeChange?.text = "₹ ${response.data[0].oneDay?.volumeChange}"
                    }
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

        //initializing viewPagerAdapter
        if (isChangePositive != null)
            chartViewPagerAdapter = ChartViewPagerAdapter(parentFragmentManager, lifecycle, currencyId!!, isChangePositive!!)
        else
            navController.popBackStack()

        binding?.viewPager?.adapter = chartViewPagerAdapter
        //disabling swiping of viewPager
        binding?.viewPager?.isUserInputEnabled = false

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

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}