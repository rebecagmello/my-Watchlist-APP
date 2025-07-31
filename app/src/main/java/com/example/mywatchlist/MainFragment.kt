package com.example.mywatchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywatchlist.data.db.AppDatabase
import com.example.mywatchlist.data.entity.WatchlistMovie
import com.example.mywatchlist.databinding.FragmentMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WatchlistViewModel by viewModels {
        val dao = AppDatabase.getDatabase(requireContext().applicationContext).watchlistDao()
        WatchlistViewModelFactory(dao)
    }

    private lateinit var adapter: WatchlistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = WatchlistAdapter(
            onItemClick = { movie ->
                //do nothing
            },
            onRemoveClick = { movie ->
                viewModel.removeMovie(movie)
            }
        )

        binding.recyclerWatchlist.adapter = adapter
        binding.recyclerWatchlist.layoutManager = LinearLayoutManager(requireContext())

        viewModel.watchlist.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            binding.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.btnAddMovie.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
            val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.nav_bottom)
            bottomNav.selectedItemId = R.id.searchFragment
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
