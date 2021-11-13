package dev.jaym21.cryptowatch.data

import androidx.lifecycle.LiveData
import dev.jaym21.cryptowatch.database.WatchlistDAO
import dev.jaym21.cryptowatch.model.Watchlist

class WatchlistRepository(private val watchlistDAO: WatchlistDAO) {

    suspend fun addCurrency(watchlist: Watchlist) = watchlistDAO.insertCurrency(watchlist)

    suspend fun removeCurrency(watchlist: Watchlist) = watchlistDAO.deleteCurrency(watchlist)

    fun getAllCurrencies(): LiveData<List<Watchlist>> = watchlistDAO.getAllCurrencies()
}