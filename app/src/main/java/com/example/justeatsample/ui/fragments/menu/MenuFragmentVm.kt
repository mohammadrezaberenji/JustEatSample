package com.example.justeatsample.ui.fragments.menu

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuFragmentVm @Inject constructor(private val useCase: GetMenuUseCase) : ViewModel() {

    private val TAG = MenuFragmentVm::class.java.simpleName

    private val _items = MutableLiveData<ResponseState<MenuItemsEntity>>()
    val items: LiveData<ResponseState<MenuItemsEntity>> = _items
    var listOfRestaurants = ArrayList<Restaurant>()
    var listOfSortValues = ArrayList<SortValueItem>()

    fun getList() {
        viewModelScope.launch {
            useCase.getMenu().collect() {
//                if (it is ResponseState.Success) {
//
//                    _items.postValue(ResponseState.Success(data = it.data))
//                } else {
////                    _items.postValue(it)
//
//                }
//            }
                if (it is com.example.justeatsample.ResponseState.Success) {
                    listOfRestaurants = it.data!!
//                    _items.postValue(it)
                }


            }
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