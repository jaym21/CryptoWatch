package dev.jaym21.cryptowatch.ui.watchlist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dev.jaym21.cryptowatch.data.WatchlistRepository
import dev.jaym21.cryptowatch.database.WatchlistDatabase
import dev.jaym21.cryptowatch.model.Watchlist

class WatchlistViewModel(application: Application): AndroidViewModel(application) {

    private val repo: WatchlistRepository
    val allCurrenciesInWatchlist: LiveData<List<Watchlist>>

    init {
        val dao = WatchlistDatabase.getDatabase(application).getWatchlistDAO()
        repo = WatchlistRepository(dao)
        allCurrenciesInWatchlist = repo.getAllCurrencies()
    }
}