package com.example.mywatchlist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywatchlist.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = SearchAdapter(
            onItemClick = { movie ->
                val bundle = Bundle().apply {
                    putParcelable("movie", movie)
                }
                findNavController().navigate(R.id.action_searchFragment_to_addMovieFragment, bundle)
            },
            onAddClick = { movie ->
                val bundle = Bundle().apply {
                    putParcelable("movie", movie)
                }
                findNavController().navigate(R.id.action_searchFragment_to_addMovieFragment, bundle)
            }
        )

        binding.recyclerSearch.adapter = adapter
        binding.recyclerSearch.layoutManager = LinearLayoutManager(requireContext())

        binding.etSearch.addTextChangedListener { text ->
            text?.toString()?.let {
                if (it.length >= 3) {
                    viewModel.searchMovies(it)
                }
            }
        }

        viewModel.searchResults.observe(viewLifecycleOwner) { results ->
            adapter.submitList(results)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
