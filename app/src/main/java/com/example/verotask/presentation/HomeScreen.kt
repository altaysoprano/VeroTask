package com.example.verotask.presentation

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.navigation.fragment.findNavController
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.verotask.R
import com.example.verotask.data.models.Task
import com.example.verotask.databinding.FragmentHomeScreenBinding
import com.example.verotask.util.RefreshWorker
import com.example.verotask.util.Resource
import com.example.verotask.util.enable
import com.example.verotask.util.startNewActivity
import com.example.verotask.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeScreen : Fragment() {

    private lateinit var binding: FragmentHomeScreenBinding
    private var searchText: String = ""
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        setHasOptionsMenu(true)

        handleBackPressed()
        setSearching()
        setupRecyclerView()
        getTasks()
        setOnSwipe()
        observeGetTasks()
        observeSwiping()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                        state.data?.let { viewModel.setOriginalTasks(it) }
                        state.data?.let { updateList(it) }
                        viewModel.filterTasks(searchText)
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                is Resource.Success -> {
                    binding.progressBarHome.visible(false)
                    viewModel.setOriginalTasks(state.data)
                    updateList(state.data)
                    getQrCodeResults()
                    viewModel.filterTasks(searchText)
                }
            }
        }
    }

    private fun observeSwiping() {
        viewModel.updateTasksState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.swipeRefreshLayoutHome.isRefreshing = true
                }

                is Resource.Error -> {
                    binding.swipeRefreshLayoutHome.isRefreshing = false
                    if (state.message == "Unauthorized") {
                        requireActivity().startNewActivity(AuthActivity::class.java)
                    } else {
                        state.data?.let { viewModel.setOriginalTasks(it) }
                        state.data?.let { updateList(it) }
                        viewModel.filterTasks(searchText)
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                is Resource.Success -> {
                    binding.swipeRefreshLayoutHome.isRefreshing = false
                    viewModel.setOriginalTasks(state.data)
                    updateList(state.data)
                    viewModel.filterTasks(searchText)
                }
            }
        }
    }

    private fun getQrCodeResults() {
        val qrResult = arguments?.getString("qrResult")
        if (!qrResult.isNullOrEmpty()) {
            binding.searchEditText.setText(qrResult.toString())
            searchText = qrResult.toString()
            viewModel.filterTasks(searchText)
            val updatedArgs = Bundle()
            arguments = updatedArgs
        }
    }

    private fun getTasks() {
        viewModel.getTasks()
    }

    private fun setOnSwipe() {
        binding.swipeRefreshLayoutHome.setOnRefreshListener {
            viewModel.updateTasks()
        }
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter()
        binding.recyclerViewTasks.adapter = recyclerViewAdapter
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateList(data: List<Task>) {
        recyclerViewAdapter.updateList(data)
        binding.noResultLayout.visibility = if (data.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun setSearching() {
        binding.clearSearch.setOnClickListener {
            binding.searchEditText.text.clear()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val isVisible = s?.isNotEmpty() ?: false
                binding.clearSearch.visibility = if (isVisible) View.VISIBLE else View.GONE
                searchText = s.toString()
                viewModel.filterTasks(searchText)
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })

        viewModel.filteredTasks.observe(viewLifecycleOwner) { filteredTasks ->
            updateList(filteredTasks)
        }
    }

    private fun handleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (binding.searchEditText.isFocused) {
                binding.searchEditText.clearFocus()
            } else if (binding.searchEditText.text.isNotEmpty()) {
                binding.searchEditText.text.clear()
                binding.searchEditText.clearFocus()
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_scan -> {
                val navController = this.findNavController()
                navController.navigate(R.id.action_homeScreen_to_scanScreen)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}