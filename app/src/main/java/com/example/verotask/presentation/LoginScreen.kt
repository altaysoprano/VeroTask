package com.example.verotask.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.verotask.databinding.FragmentLoginScreenBinding
import com.example.verotask.util.Resource
import com.example.verotask.util.enable
import com.example.verotask.util.startNewActivity
import com.example.verotask.util.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen : Fragment() {

    private lateinit var binding: FragmentLoginScreenBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginScreenBinding.inflate(inflater, container, false)

        binding.buttonLogin.setOnClickListener {
            val username = binding.editTextUsername.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            viewModel.login(username, password)
        }

        setButtonEnabled()

        observeLogin()

        return binding.root
    }

    private fun observeLogin() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBarLogin.visible(true)
                    binding.buttonLogin.enable(false)
                }

                is Resource.Error -> {
                    binding.progressBarLogin.visible(false)
                    binding.buttonLogin.enable(true)
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Success -> {
                    binding.progressBarLogin.visible(false)
                    binding.buttonLogin.enable(true)
                    requireActivity().startNewActivity(HomeActivity::class.java)
                    Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun setButtonEnabled() {
        binding.buttonLogin.enable(false)
        binding.editTextPassword.addTextChangedListener {
            val username = binding.editTextUsername.text.toString().trim()
            binding.buttonLogin.enable(username.isNotEmpty() && it.toString().isNotEmpty())
        }

        binding.editTextUsername.addTextChangedListener {
            val password = binding.editTextPassword.text.toString().trim()
            binding.buttonLogin.enable(password.isNotEmpty() && it.toString().isNotEmpty())
        }
    }
}
