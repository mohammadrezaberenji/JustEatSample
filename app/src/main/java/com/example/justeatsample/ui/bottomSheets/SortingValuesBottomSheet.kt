package com.example.justeatsample.ui.bottomSheets

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.justeatsample.data.source.local_models.SortValueItem
import com.example.justeatsample.databinding.SortingValuesBottomSheetBinding
import com.example.justeatsample.ui.adapters.SortValuesAdapter
import com.example.justeatsample.ui.base.BaseBottomSheetDialog

class SortingValuesBottomSheet(
    private val sortingValues: ArrayList<SortValueItem>,
    private val lister: (position: Int) -> Unit,
) :
    BaseBottomSheetDialog() {

    private val TAG = SortingValuesBottomSheet::class.java.simpleName
    private var _binding: SortingValuesBottomSheetBinding? = null
    private val binding get() = _binding!!
    private var selectedFilterPositionInList: Int = -1
    private val adapter = SortValuesAdapter(::onItemClickInRv)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SortingValuesBottomSheetBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        findIndexOfSelectedFilter()
    }


    private fun setUpView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        Log.i("TAG", "setUpView: item last position : $selectedFilterPositionInList")
        adapter.setList(sortingValues)
        binding.recyclerView.adapter = adapter
        binding.confirmBtn.setOnClickListener {
            lister.invoke(selectedFilterPositionInList)
            dismiss()
        }

        binding.resetTv.setOnClickListener {
            lister.invoke(0)
            dismiss()
        }
        binding.recyclerView.itemAnimator = null


    }

    private fun onItemClickInRv(position: Int) {
        Log.i(TAG, "onItemClickInRv: ")
        selectedFilterPositionInList = position
        applyChangesToList(position)

    }

    private fun applyChangesToList(position: Int) {
        Log.i(TAG, "applyChangesToList: ")
        val copyOfList = ArrayList<SortValueItem>()
        copyOfList.addAll(sortingValues)


        val index = copyOfList.indexOf(copyOfList.find { it.isSelected })
        Log.i(TAG, "findIndexOfSelectedFilter: index : $index")

        copyOfList[index] = SortValueItem(
            sortingValuesEnum = copyOfList.get(index).sortingValuesEnum,
            isSelected = false,
            name = copyOfList.get(index).name
        )

        Log.i(TAG, "onItemClickInRv: index : $index ")
        Log.i(TAG, "onItemClickInRv: item list position : $selectedFilterPositionInList")
        copyOfList[position] = SortValueItem(
            sortingValuesEnum = sortingValues.get(position).sortingValuesEnum,
            isSelected = true,
            name = sortingValues.get(position).name
        )


        adapter.setList(copyOfList)
    }

    private fun findIndexOfSelectedFilter() {
        selectedFilterPositionInList = sortingValues.indexOf(sortingValues.find { it.isSelected })

    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}