package com.example.justeatsample.ui.activities.main

import android.os.Bundle
import android.window.SplashScreen

import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.justeatsample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var splash: androidx.core.splashscreen.SplashScreen


    override fun onCreate(savedInstanceState: Bundle?) {
         splash = installSplashScreen()
        splash.setKeepOnScreenCondition { true }
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        GlobalScope.launch {
            delay(3000)
            splash.setKeepOnScreenCondition { false }

        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}