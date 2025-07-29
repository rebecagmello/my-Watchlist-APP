package com.example.mywatchlist.data.api

import com.example.mywatchlist.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path



interface TMDbApiService {

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("api_key") apiKey: String
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): MovieResponse

}
