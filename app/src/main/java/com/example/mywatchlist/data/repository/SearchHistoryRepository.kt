package com.example.mywatchlist.data.repository

import com.example.mywatchlist.data.dao.SearchHistoryDao
import com.example.mywatchlist.data.entity.SearchHistoryMovie

class SearchHistoryRepository(private val dao: SearchHistoryDao) {
    val recentSearches = dao.getRecentSearches()

    suspend fun insert(movie: SearchHistoryMovie) = dao.insert(movie)

    suspend fun clear() = dao.clearHistory()
}
