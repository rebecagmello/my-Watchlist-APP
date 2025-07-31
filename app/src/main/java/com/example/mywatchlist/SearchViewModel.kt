package com.example.mywatchlist

import android.app.Application
import androidx.lifecycle.*
import com.example.mywatchlist.data.db.AppDatabase
import com.example.mywatchlist.data.entity.SearchHistoryMovie
import com.example.mywatchlist.data.model.Movie
import com.example.mywatchlist.data.repository.SearchHistoryRepository
import kotlinx.coroutines.launch
import com.example.mywatchlist.data.api.RetrofitInstance
import com.example.mywatchlist.util.Constants

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).searchHistoryDao()
    private val repository = SearchHistoryRepository(dao)

    private val _searchResults = MutableLiveData<List<Movie>>()
    val searchResults: LiveData<List<Movie>> = _searchResults
    val recentSearches = repository.recentSearches

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

    fun saveSearch(movie: Movie) {
        viewModelScope.launch {
            val searchHistoryMovie = SearchHistoryMovie(
                id = movie.id,
                title = movie.title,
                posterPath = movie.posterPath
            )
            repository.insert(searchHistoryMovie)
        }
    }
}
