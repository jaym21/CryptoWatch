package dev.jaym21.cryptowatch.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.jaym21.cryptoapi.models.entities.NewsData
import dev.jaym21.cryptowatch.data.CryptoCompareRepository
import dev.jaym21.cryptowatch.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel: ViewModel() {

    private val repo = CryptoCompareRepository()

    //live data for news response
    val news: MutableLiveData<ApiResponse<List<NewsData>>> = MutableLiveData()

    fun getNews() = viewModelScope.launch(Dispatchers.IO) {
        news.postValue(ApiResponse.Loading())

        val response = repo.getLatestNews()
        if (response != null) {
            news.postValue(ApiResponse.Success(response))
        }else {
            news.postValue(ApiResponse.Error("Could not retrieve news, try again!"))
        }
    }
}