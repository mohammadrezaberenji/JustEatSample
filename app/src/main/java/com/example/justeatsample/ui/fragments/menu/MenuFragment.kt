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
import com.ethanhua.skeleton.Skeleton
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
        _binding =
            FragmentMenuBinding.inflate(LayoutInflater.from(inflater.context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.listOfRestaurants.isNotEmpty()) {
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = adapter
            adapter.setList(viewModel.listOfRestaurants)
        } else {
            initObserver()
            viewModel.getList()
        }

        setUpToolbar()


    }

    override fun onResume() {
        super.onResume()
    }


    private fun onItemClick(position: Int) {
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
            val bottomSheet = SortingValuesBottomSheet(viewModel.listOfSortValues, ::filerApplied)
            bottomSheet.show(childFragmentManager, "tag")
        }
    }

    private fun filerApplied(position: Int) {
        viewModel.applyChangesToSortList(position)
        adapter.setList(viewModel.filterList())
    }

    private fun initObserver() {
        viewModel.items.observe(viewLifecycleOwner) {
            when (it) {
                is ResponseState.Success -> {
                    initRecyclerView()

                }
                is ResponseState.Error -> {

                }

                is ResponseState.Loading -> {
//                    val skeleton = Skeleton.bind(binding.recyclerView)
//                        .load(R.layout.menu_item_adapter)
//                    skeleton.show()

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
        findNavController().navigate(
            MenuFragmentDirections.actionToDetailsFragment(
                viewModel.listOfRestaurants[position]
            )
        )

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }



}