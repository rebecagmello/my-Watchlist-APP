package com.example.mywatchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mywatchlist.data.dao.WatchlistDao

class WatchlistViewModelFactory(private val dao: WatchlistDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WatchlistViewModel::class.java)) {
            return WatchlistViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
