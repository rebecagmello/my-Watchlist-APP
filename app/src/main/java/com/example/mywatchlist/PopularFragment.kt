package com.example.mywatchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywatchlist.databinding.FragmentPopularBinding
import com.example.mywatchlist.data.model.Movie

class PopularFragment : Fragment() {

    private var _binding: FragmentPopularBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularViewModel by viewModels()
    private lateinit var adapter: PopularAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = PopularAdapter(
            onItemClick = { movie ->
                val bundle = Bundle().apply {
                    putParcelable("movie", movie)
                }
                findNavController().navigate(
                    R.id.action_popularFragment_to_addMovieFragment,
                    bundle
                )
            },
            onAddClick = { movie ->
                val bundle = Bundle().apply {
                    putParcelable("movie", movie)
                }
                findNavController().navigate(
                    R.id.action_popularFragment_to_addMovieFragment,
                    bundle
                )
            }
        )

        binding.recyclerPopular.adapter = adapter
        binding.recyclerPopular.layoutManager = LinearLayoutManager(requireContext())

        viewModel.popularResults.observe(viewLifecycleOwner) { movies ->
            adapter.submitList(movies)
        }

        viewModel.popularMovies()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
