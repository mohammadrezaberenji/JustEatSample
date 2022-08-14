package com.example.justeatsample.ui.activities.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.remote.apiModel.MenuResp
import com.example.justeatsample.data.source.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityVm @Inject constructor(private val repository: Repository) : ViewModel() {

    private val list = ArrayList<Restaurant>()


    private val _items = MutableLiveData<ResponseState<MenuItemsEntity>>()
    val items: LiveData<ResponseState<MenuItemsEntity>> = _items

    fun getList() {
        viewModelScope.launch {
            repository.getList().collect() {
                _items.postValue(it)
                list.addAll(it.data?.restaurants ?: arrayListOf())
            }
        }
    }

    fun updateModel() {
        viewModelScope.launch {
//            val item = list[0]
//            item.name = "sedf"
//            repository.updateItem(item)
        }
    }

}