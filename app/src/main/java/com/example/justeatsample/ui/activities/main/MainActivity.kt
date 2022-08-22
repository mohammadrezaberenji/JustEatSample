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
    private val viewModel: MainActivityVm by viewModels()
    private val adapter = MenuAdapter(onItemClick = ::onItemClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObserver()
        viewModel.getList()
        setUpToolbar()
    }

    private fun initObserver() {
        Log.i(TAG, "initObserver: init observer")
        viewModel.items.observe(this) {
            when (it) {
                is ResponseState.Success -> {
//                    Log.i(TAG, "initObserver: response state is ok ${it.data?.getSortedList()}")
                    initRecyclerView()
//                    viewModel.updateModel()

                }
                is ResponseState.Error -> {

                }

                is ResponseState.Loading -> {

                }

            }
        }
    }

    private fun initRecyclerView() {
        Log.i(TAG, "initRecyclerView: ")
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        Log.i(TAG, "initRecyclerView: items")
        adapter.setList(viewModel.listOfRestaurants)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onItemClick(position: Int) {
        Log.i(TAG, "onItemClick: position :$position")
        viewModel.listOfRestaurants[position].isFavorite =
            !viewModel.listOfRestaurants[position].isFavorite
        adapter.likeItem(position)
        viewModel.updateModel(
            viewModel.listOfRestaurants[position].isFavorite,
            viewModel.listOfRestaurants[position].id
        )

    }

    private fun setUpToolbar() {
        binding.toolbar.filterIv.isVisible = true
        binding.toolbar.titleTv.text = getString(R.string.menu)
        binding.toolbar.filterIv.setOnClickListener {
            Log.i(TAG, "setUpToolbar: list : ${viewModel.listOfSortValues}")
            val bottomSheet = SortingValuesBottomSheet(viewModel.listOfSortValues, ::filerApplied)
            bottomSheet.show(supportFragmentManager, "tag")
        }
    }

    private fun filerApplied(position: Int) {
        Log.i(TAG, "filerApplied: which sort they want : ${viewModel.listOfSortValues[position]}")
        viewModel.applyChangesToSortList(position)
        Log.i(TAG, "filerApplied: viewModel : sorted List : ${viewModel.filterList()}")
        adapter.setList(viewModel.filterList())
    }

//    private fun changeStatusBar() {
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor =
//            ContextCompat.getColor(this, R.color.white)
//    }
}