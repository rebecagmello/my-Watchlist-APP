package com.example.mywatchlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mywatchlist.data.entity.WatchlistMovie
import com.example.mywatchlist.databinding.ItemWatchlistBinding

class WatchlistAdapter(
    private val onItemClick: (WatchlistMovie) -> Unit,
    private val onRemoveClick: (WatchlistMovie) -> Unit
) : ListAdapter<WatchlistMovie, WatchlistAdapter.MovieViewHolder>(DiffCallback) {

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<WatchlistMovie>() {
            override fun areItemsTheSame(oldItem: WatchlistMovie, newItem: WatchlistMovie) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: WatchlistMovie, newItem: WatchlistMovie) =
                oldItem == newItem
        }
    }
    inner class MovieViewHolder(
        private val binding: ItemWatchlistBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: WatchlistMovie) {
            binding.tvTitle.text = movie.title
            binding.tvYear.text = movie.year
            binding.tvDate.text = "Scheduled for: ${movie.scheduledDate}"

            Glide.with(binding.root.context)
                .load(movie.posterUrl)
                .into(binding.ivPoster)

            binding.root.setOnClickListener { onItemClick(movie) }
            binding.btnRemove.setOnClickListener { onRemoveClick(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemWatchlistBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
