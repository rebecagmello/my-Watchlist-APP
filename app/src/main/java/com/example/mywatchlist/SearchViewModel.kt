package com.example.mywatchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mywatchlist.data.api.RetrofitInstance
import com.example.mywatchlist.data.api.TMDbApiService
import com.example.mywatchlist.data.model.Movie
import kotlinx.coroutines.launch

import com.example.mywatchlist.util.Constants

class SearchViewModel : ViewModel() {

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults

    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.searchMovies(query, Constants.TMDB_API_KEY)
                _searchResults.value = response.results
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            }
        }
    }
}

