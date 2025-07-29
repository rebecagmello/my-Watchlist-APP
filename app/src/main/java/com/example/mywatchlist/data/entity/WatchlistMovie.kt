package com.example.mywatchlist.data.entity

import androidx.room.PrimaryKey
import androidx.room.Entity


@Entity(tableName = "watchlist")
data class WatchlistMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val year: String,
    val posterUrl: String,
    val scheduledDate: String
)
