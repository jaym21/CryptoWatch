package dev.jaym21.cryptowatch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watchlist_table")
data class Watchlist (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val symbol: String,
    val watchlist: String
)