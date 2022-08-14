package com.example.justeatsample.ui.activities.main

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justeatsample.R
import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.remote.apiModel.RestaurantResp
import com.example.justeatsample.databinding.ActivityMainBinding
import com.example.justeatsample.ui.adapters.MenuAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActivityVm by viewModels()
    private val adapter = MenuAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initObserver()
        viewModel.getList()
    }

    private fun initObserver() {
        Log.i(TAG, "initObserver: init observer")
        viewModel.items.observe(this) {
            when (it) {
                is ResponseState.Success -> {
                    Log.i(TAG, "initObserver: response state is ok ${it.data?.restaurants}")
                    initRecyclerView(arrayList = it.data?.restaurants ?: arrayListOf())
                    viewModel.updateModel()

                }
                is ResponseState.Error -> {
                    Log.e(TAG, "initObserver: error ")

                }

                is ResponseState.Loading -> {

                }

            }
        }
    }

    private fun initRecyclerView(arrayList: ArrayList<Restaurant>) {
        Log.i(TAG, "initRecyclerView: ")
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        adapter.setList(arrayList)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

//    private fun changeStatusBar() {
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor =
//            ContextCompat.getColor(this, R.color.white)
//    }
}