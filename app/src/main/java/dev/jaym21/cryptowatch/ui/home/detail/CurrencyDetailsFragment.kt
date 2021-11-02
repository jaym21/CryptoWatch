package dev.jaym21.cryptowatch.ui.home.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dev.jaym21.cryptowatch.databinding.FragmentCurrencyDetailsBinding
class CurrencyDetailsFragment : Fragment() {

    private var binding: FragmentCurrencyDetailsBinding? = null
    private lateinit var viewModel: CurrencyDetailsViewModel

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

        viewModel = ViewModelProvider(this).get(CurrencyDetailsViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}