package dev.jaym21.cryptowatch.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dev.jaym21.cryptowatch.databinding.FragmentArticleOpenBinding

class ArticleOpenFragment : Fragment() {

    private var binding: FragmentArticleOpenBinding? = null
    private lateinit var navController: NavController
    private var articleUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticleOpenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing navController
        navController = Navigation.findNavController(view)

        //getting article link from news item clicked in NewsFragment
        articleUrl = arguments?.getString("articleUrl")

        if (articleUrl != null) {
            //showing progress bar
            binding?.progressBar?.visibility = View.VISIBLE
            //passing the url of the clicked article to webViewClient to view full article
            binding?.webView?.apply {
                webViewClient = WebViewClient()
                loadUrl(articleUrl!!)
                //hiding progress bar
                binding?.progressBar?.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}