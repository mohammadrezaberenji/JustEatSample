package com.example.justeatsample.ui.fragments.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.local_models.SortValueItem
import com.example.justeatsample.data.source.repository.Repository
import com.example.justeatsample.domain.GetMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuFragmentVm @Inject constructor(private val useCase: GetMenuUseCase) : ViewModel() {

    private val TAG = MenuFragmentVm::class.java.simpleName

    private val _items = MutableLiveData<ResponseState<ArrayList<Restaurant>>>()
    val items: LiveData<ResponseState<ArrayList<Restaurant>>> = _items
    var listOfRestaurants = ArrayList<Restaurant>()
    var listOfSortValues = ArrayList<SortValueItem>()

    fun getList() {
        Log.i(TAG, "getList: from view model ")
        viewModelScope.launch {
            useCase.getMenu().collect {
                Log.i(TAG, "getList: get get list : ${it.data}")
                listOfRestaurants = it.data!!
                _items.postValue(ResponseState.Success(listOfRestaurants))
            }
//            delay(3000)
//            useCase.stategetFlow.collect {
//                Log.i(TAG, "getList: list of items $it")
//                listOfRestaurants = it
//                _items.postValue(ResponseState.Success(it))
//            }

        }


    }

    fun filterList(): ArrayList<Restaurant> {
        return useCase.filterList()
    }

    fun updateModel(boolean: Boolean, id: String) {
        viewModelScope.launch {
            useCase.updateModel(boolean, id)
        }
    }

    fun applyChangesToSortList(position: Int) {
        useCase.applyChanges(position)
    }
}