package com.example.mywatchlist.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryMovie(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val posterPath: String?,
    val timestamp: Long = System.currentTimeMillis()
)
