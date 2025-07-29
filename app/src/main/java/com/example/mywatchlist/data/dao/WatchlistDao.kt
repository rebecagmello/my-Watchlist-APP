package com.example.mywatchlist.data.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Insert
import com.example.mywatchlist.data.entity.WatchlistMovie

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist ORDER BY scheduledDate ASC")
    fun getAll(): LiveData<List<WatchlistMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: WatchlistMovie)

    @Delete
    suspend fun delete(movie: WatchlistMovie)

    @Query("SELECT * FROM watchlist WHERE id = :movieId LIMIT 1")
    suspend fun getById(movieId: Int): WatchlistMovie?
}
