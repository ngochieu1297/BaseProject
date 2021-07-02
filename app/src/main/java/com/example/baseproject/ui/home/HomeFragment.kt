package com.example.baseproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.baseproject.R
import com.example.baseproject.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomeAdapter
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
            it.viewModel = homeViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        addEvent()
        addObserver()
    }

    private fun setupView() {
        homeAdapter = HomeAdapter { item ->
            findNavController().navigate(R.id.detailFragment, bundleOf("item" to item))
        }
        binding.recyclerUser.adapter = homeAdapter
        binding.recyclerUser.setHasFixedSize(true)
    }

    private fun addEvent() {
        binding.buttonSearch.setOnClickListener {
            homeViewModel.searchUser()
        }
    }

    private fun addObserver() {
        homeViewModel.usersLiveData.observe(viewLifecycleOwner, {
            homeAdapter.setData(it)
        })
    }
}
