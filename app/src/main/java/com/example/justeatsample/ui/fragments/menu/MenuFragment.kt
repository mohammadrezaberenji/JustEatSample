package com.example.justeatsample.ui.fragments.menu

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justeatsample.R
import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.databinding.FragmentMenuBinding
import com.example.justeatsample.ui.adapters.MenuAdapter
import com.example.justeatsample.ui.bottomSheets.SortingValuesBottomSheet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private val TAG = MenuFragment::class.java.simpleName

    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MenuFragmentVm by viewModels()
    private val adapter =
        MenuAdapter(onLickClick = ::onItemClick, onMenuItemClick = ::onMenuItemClick)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView: ")
        _binding =
            FragmentMenuBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.listOfRestaurants.isNotEmpty()) {
            Log.i(TAG, "onViewCreated: bundle is not null : ${viewModel.listOfRestaurants}")
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
            adapter.setList(viewModel.listOfRestaurants)
        } else {
            Log.i(TAG, "onViewCreated: bundle is null")
            initObserver()
            viewModel.getList()
        }

        setUpToolbar()


    }

    override fun onResume() {
        Log.i(TAG, "onResume: ")
        super.onResume()
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
            bottomSheet.show(childFragmentManager, "tag")
        }
    }

    private fun filerApplied(position: Int) {
        Log.i(TAG, "filerApplied: which sort they want : ${viewModel.listOfSortValues[position]}")
        viewModel.applyChangesToSortList(position)
        Log.i(TAG, "filerApplied: viewModel : sorted List : ${viewModel.filterList()}")
        adapter.setList(viewModel.filterList())
    }

    private fun initObserver() {
        Log.i(TAG, "initObserver: init observer")
        viewModel.items.observe(viewLifecycleOwner) {
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
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
        Log.i(TAG, "initRecyclerView: items")
        adapter.setList(viewModel.listOfRestaurants)


    }

    private fun onMenuItemClick(position: Int) {
        Log.i(TAG, "onMenuItemClick: position :$position")
        findNavController().navigate(
            MenuFragmentDirections.actionToDetailsFragment(
                viewModel.listOfRestaurants[position]
            )
        )

    }

    override fun onDestroyView() {
        Log.i(TAG, "onDestroyView: ")
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable("salam", viewModel.listOfRestaurants)
    }


}