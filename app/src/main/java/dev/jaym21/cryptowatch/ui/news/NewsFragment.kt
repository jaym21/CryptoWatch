package dev.jaym21.cryptowatch.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import dev.jaym21.cryptowatch.R
import dev.jaym21.cryptowatch.adapters.NewsRVAdapter
import dev.jaym21.cryptowatch.databinding.FragmentNewsBinding

class NewsFragment : Fragment() {

    private var binding: FragmentNewsBinding? = null
    private val newsAdapter = NewsRVAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing recyclerView
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        binding?.rvNews?.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}