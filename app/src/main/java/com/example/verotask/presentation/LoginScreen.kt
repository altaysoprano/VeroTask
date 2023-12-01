package com.example.verotask.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.verotask.databinding.FragmentLoginScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginScreen : Fragment() {

    private lateinit var binding: FragmentLoginScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginScreenBinding.inflate(inflater, container, false)

        binding.buttonLogin.setOnClickListener {

        }

        return binding.root
    }
}