package com.example.verotask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.verotask.databinding.ActivityMainBinding
import com.example.verotask.presentation.FragmentNavigation
import com.example.verotask.presentation.ui.LoginScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentNavigation {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.coordinator, LoginScreen())
                .commit()
        }
    }

    override fun navigateFrag(fragment: Fragment, addToStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        transaction.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out,
            R.anim.fade_in,
            R.anim.fade_out
        )

        transaction.replace(R.id.coordinator, fragment)
/*
        when (fragment) {
            is LoginScreen -> {
                if (!addToStack) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                }
            }
        }
*/

        if (addToStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

}