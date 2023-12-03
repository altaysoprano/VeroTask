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
        (activity as AppCompatActivity?)!!.title = "Home"
        (activity as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        setHasOptionsMenu(true)

        setupRecyclerView()
        getTasks()

        return binding.root
    }

    private fun observeLogin() {
        viewModel.getTasksState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBarHome.visible(true)
                }

                is Resource.Error -> {
                    binding.progressBarHome.visible(false)
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Success -> {
                    binding.progressBarHome.visible(false)
                    Log.d("Mesaj: ", "Successte şu an")

                    Log.d("Mesaj: ", "Liste boş mu? ${state.data.isEmpty()}")
                    Log.d("Mesaj: ", "Liste boyutu? ${state.data.size}")
                    updateList(state.data)
                }
            }
        }
    }

    private fun getTasks() {
        viewModel.getTasks()
        observeLogin()
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