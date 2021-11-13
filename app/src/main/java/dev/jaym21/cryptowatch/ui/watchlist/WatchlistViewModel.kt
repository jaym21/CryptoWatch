package dev.jaym21.cryptowatch.ui.watchlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dev.jaym21.cryptoapi.models.responses.CurrencyResponse
import dev.jaym21.cryptowatch.data.NomicsRepository
import dev.jaym21.cryptowatch.data.WatchlistRepository
import dev.jaym21.cryptowatch.database.WatchlistDatabase
import dev.jaym21.cryptowatch.model.Watchlist
import dev.jaym21.cryptowatch.utils.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchlistViewModel(application: Application): AndroidViewModel(application) {

    private val watchlistRepository: WatchlistRepository
    private val nomicsRepository = NomicsRepository()
    val allCurrenciesInWatchlist: LiveData<List<Watchlist>>
    val requiredCurrencies: MutableLiveData<ApiResponse<List<CurrencyResponse>>> = MutableLiveData()

    init {
        val dao = WatchlistDatabase.getDatabase(application).getWatchlistDAO()
        watchlistRepository = WatchlistRepository(dao)
        allCurrenciesInWatchlist = watchlistRepository.getAllCurrencies
    }

    fun addCurrencyToWatchlist(watchlist: Watchlist) = viewModelScope.launch(Dispatchers.IO) {
        watchlistRepository.addCurrency(watchlist)
    }

    fun removeCurrencyFromWatchlist(watchlist: Watchlist) = viewModelScope.launch(Dispatchers.IO) {
        watchlistRepository.removeCurrency(watchlist)
    }

    fun fetchCurrencies(ids: String) = viewModelScope.launch(Dispatchers.IO) {
        //as we are going to make network call so showing loading progress bar
        requiredCurrencies.postValue(ApiResponse.Loading())

        //getting response from repo
        val response = nomicsRepository.fetchCurrencies(ids)
        //checking if we got a successful response
        if (response != null){
            requiredCurrencies.postValue(ApiResponse.Success(response))
        }else {
            requiredCurrencies.postValue(ApiResponse.Error("Could not retrieve currencies, try again!"))
        }
    }
}