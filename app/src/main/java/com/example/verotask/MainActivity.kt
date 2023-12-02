package com.example.verotask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.verotask.databinding.ActivityMainBinding
import com.example.verotask.presentation.AuthActivity
import com.example.verotask.presentation.HomeActivity
import com.example.verotask.util.AccessTokenDataStore
import com.example.verotask.util.startNewActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var accessTokenDataStore: AccessTokenDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        accessTokenCheck()
    }

    private fun accessTokenCheck() {
        lifecycleScope.launch {
            val accessToken = accessTokenDataStore.getAccessToken()
            Log.d("Mesaj: ", "Access Token: $accessToken")
            val activity = if (accessToken.isNotEmpty()) HomeActivity::class.java else AuthActivity::class.java

            startNewActivity(activity)
        }
    }

    private fun navigateToLoginScreen() {
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}
