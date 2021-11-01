package dev.jaym21.cryptowatch.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.databinding.FragmentArticleOpenBinding

class ArticleOpenFragment : Fragment() {

    private var binding: FragmentArticleOpenBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticleOpenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}