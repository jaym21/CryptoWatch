package dev.jaym21.cryptowatch.ui.watchlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import dev.jaym21.cryptowatch.data.WatchlistRepository
import dev.jaym21.cryptowatch.database.WatchlistDatabase
import dev.jaym21.cryptowatch.model.Watchlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchlistViewModel(application: Application): AndroidViewModel(application) {

    private val repo: WatchlistRepository
    val allCurrenciesInWatchlist: LiveData<List<Watchlist>>

    init {
        val dao = WatchlistDatabase.getDatabase(application).getWatchlistDAO()
        repo = WatchlistRepository(dao)
        allCurrenciesInWatchlist = repo.getAllCurrencies
    }

    fun addCurrencyToWatchlist(watchlist: Watchlist) = viewModelScope.launch(Dispatchers.IO) {
        repo.addCurrency(watchlist)
    }

    fun removeCurrencyFromWatchlist(watchlist: Watchlist) = viewModelScope.launch(Dispatchers.IO) {
        repo.removeCurrency(watchlist)
    }
}