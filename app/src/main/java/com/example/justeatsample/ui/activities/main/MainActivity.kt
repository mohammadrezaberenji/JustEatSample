package com.example.justeatsample.ui.activities.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justeatsample.R
import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.databinding.ActivityMainBinding
import com.example.justeatsample.ui.adapters.MenuAdapter
import com.example.justeatsample.ui.bottomSheets.SortingValuesBottomSheet
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }




    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}