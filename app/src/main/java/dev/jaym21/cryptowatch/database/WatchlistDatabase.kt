package dev.jaym21.cryptowatch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.jaym21.cryptowatch.model.Watchlist

@Database(entities = [Watchlist::class], exportSchema = false, version = 1)
abstract class WatchlistDatabase: RoomDatabase() {

    //abstract function to get DAO
    abstract fun getWatchlistDAO(): WatchlistDAO

   companion object {
       //Singleton prevents multiple instances of database opening at the same time
       @Volatile
       private var INSTANCE: WatchlistDatabase? = null

       //function to get database instance
       fun getDatabase(context: Context): WatchlistDatabase {
           // if the INSTANCE is not null, then return it,
           // if it is, then create the database
           return INSTANCE ?: synchronized(this) {
               val databaseInstance = Room.databaseBuilder(
                   context.applicationContext,
                   WatchlistDatabase::class.java,
                   "watchlist_database"
               ).build()
               INSTANCE = databaseInstance
               return INSTANCE as WatchlistDatabase
           }
       }
   }
}