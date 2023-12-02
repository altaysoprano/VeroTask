package com.example.verotask.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.verotask.databinding.FragmentLoginScreenBinding
import com.example.verotask.util.Resource
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

        observeLogin()

        return binding.root
    }

    private fun observeLogin() {
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBarLogin.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.progressBarLogin.visibility = View.GONE
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT)
                        .show()
                }

                is Resource.Success -> {
                    binding.progressBarLogin.visibility = View.GONE
                    Toast.makeText(requireContext(), "Giriş başarılı", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
