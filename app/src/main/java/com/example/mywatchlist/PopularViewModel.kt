package com.example.mywatchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.data.api.RetrofitInstance
import com.example.mywatchlist.data.model.Movie
import com.example.mywatchlist.util.Constants
import kotlinx.coroutines.launch

class PopularViewModel : ViewModel() {

    private val _popularResults = MutableLiveData<List<Movie>>()
    val popularResults: LiveData<List<Movie>> = _popularResults

    fun popularMovies() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getPopularMovies(Constants.TMDB_API_KEY)
                _popularResults.value = response.results
            } catch (e: Exception) {
                _popularResults.value = emptyList()
            }
        }
    }
}

