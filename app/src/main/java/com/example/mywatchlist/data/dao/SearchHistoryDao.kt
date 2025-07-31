package com.example.mywatchlist.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mywatchlist.data.entity.SearchHistoryMovie

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY timestamp DESC LIMIT 20")
    fun getRecentSearches(): LiveData<List<SearchHistoryMovie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: SearchHistoryMovie)

    @Query("DELETE FROM search_history")
    suspend fun clearHistory()
}
