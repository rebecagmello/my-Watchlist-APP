package com.example.mywatchlist
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.data.dao.WatchlistDao
import com.example.mywatchlist.data.entity.WatchlistMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WatchlistViewModel(private val dao: WatchlistDao) : ViewModel() {

    val watchlist: LiveData<List<WatchlistMovie>> = dao.getAll()

    fun addMovie(movie: WatchlistMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(movie)
        }
    }

    fun removeMovie(movie: WatchlistMovie) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(movie)
        }
    }
}

