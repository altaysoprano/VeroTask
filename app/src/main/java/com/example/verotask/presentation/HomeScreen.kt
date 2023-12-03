package com.example.verotask.presentation

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.verotask.R
import com.example.verotask.data.models.Task
import com.example.verotask.databinding.FragmentHomeScreenBinding
import com.example.verotask.util.Resource
import com.example.verotask.util.enable
import com.example.verotask.util.startNewActivity
import com.example.verotask.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        setHasOptionsMenu(true)

        setupRecyclerView()
        getTasks()
        setOnSwipe()
        observeGetTasks()
        observeSwiping()

        return binding.root
    }

    private fun observeGetTasks() {
        viewModel.getTasksState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBarHome.visible(true)
                }

                is Resource.Error -> {
                    binding.progressBarHome.visible(false)
                    if (state.message == "Unauthorized") {
                        requireActivity().startNewActivity(AuthActivity::class.java)
                    } else {
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                is Resource.Success -> {
                    binding.progressBarHome.visible(false)
                    updateList(state.data)
                }
            }
        }
    }

    private fun observeSwiping() {
        viewModel.swipeState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.swipeRefreshLayoutHome.isRefreshing = true
                }

                is Resource.Error -> {
                    binding.swipeRefreshLayoutHome.isRefreshing = false
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Success -> {
                    binding.swipeRefreshLayoutHome.isRefreshing = false
                    updateList(state.data)
                }
            }
        }
    }

    private fun getTasks() {
        viewModel.getTasks()
    }

    private fun setOnSwipe() {
        binding.swipeRefreshLayoutHome.setOnRefreshListener {
            viewModel.onSwipe()
        }
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter()
        binding.recyclerViewTasks.adapter = recyclerViewAdapter
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateList(data: List<Task>) {
        recyclerViewAdapter.updateList(data)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_sign_out -> {

                true
            }

            R.id.menu_item_scan -> {

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}