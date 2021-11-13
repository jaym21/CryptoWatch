package dev.jaym21.cryptowatch.database

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.jaym21.cryptowatch.model.Watchlist

@Dao
interface WatchlistDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(watchlist: Watchlist)

    @Delete
    suspend fun deleteCurrency(watchlist: Watchlist)

    @Query("SELECT * FROM watchlist_table")
    fun getAllCurrencies(): LiveData<List<Watchlist>>
}