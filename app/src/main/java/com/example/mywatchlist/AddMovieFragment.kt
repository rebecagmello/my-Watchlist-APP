package com.example.mywatchlist

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mywatchlist.data.db.AppDatabase
import com.example.mywatchlist.data.entity.WatchlistMovie
import com.example.mywatchlist.data.model.Movie
import com.example.mywatchlist.databinding.FragmentAddMovieBinding
import com.example.mywatchlist.databinding.FragmentMainBinding
import kotlin.getValue

class AddMovieFragment : Fragment() {

    private var _binding: FragmentAddMovieBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectedMovie: Movie

    private val viewModel: WatchlistViewModel by viewModels {
        val dao = AppDatabase.getDatabase(requireContext().applicationContext).watchlistDao()
        WatchlistViewModelFactory(dao)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("movie", Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            arguments?.getParcelable("movie") as? Movie
        }


        if (movie == null) {
            Toast.makeText(requireContext(), "Erro ao carregar filme", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
            return
        }

        selectedMovie = movie

        binding.tvTitle.text = selectedMovie.title
        binding.tvYear.text = "Ano: ${selectedMovie.releaseDate?.take(4) ?: "N/A"}"
        binding.tvOverview.text = "Sinopse: ${selectedMovie.overview}"

        Glide.with(requireContext())
            .load("https://image.tmdb.org/t/p/w500${selectedMovie.posterPath}")
            .into(binding.ivPoster)

        binding.btnAddMovie.setOnClickListener {
            val day = binding.datePicker.dayOfMonth
            val month = binding.datePicker.month + 1
            val year = binding.datePicker.year
            val formattedDate = "%02d/%02d/%04d".format(day, month, year)

            val movieToSave = WatchlistMovie(
                id = selectedMovie.id,
                title = selectedMovie.title,
                year = selectedMovie.releaseDate?.take(4) ?: "N/A",
                posterUrl = "https://image.tmdb.org/t/p/w500${selectedMovie.posterPath}",
                scheduledDate = formattedDate
            )

            viewModel.addMovie(movieToSave)
            Toast.makeText(requireContext(), "Filme adicionado!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addMovieFragment_to_mainFragment)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
