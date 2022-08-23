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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuFragmentVm @Inject constructor(private val repository: Repository) : ViewModel() {

    private val TAG = MenuFragmentVm::class.java.simpleName

    private val _items = MutableLiveData<ResponseState<MenuItemsEntity>>()
    val items: LiveData<ResponseState<MenuItemsEntity>> = _items
    var listOfRestaurants = ArrayList<Restaurant>()
    var listOfSortValues = ArrayList<SortValueItem>()
    private var filterTag: Restaurant.SortingValuesEnum? = null

    fun getList() {
        Log.i(TAG, "getList: ")
        viewModelScope.launch {
            repository.getList().collect() {
                if (it is ResponseState.Success) {
                    listOfRestaurants = it.data?.getSortedList() ?: arrayListOf()
                    listOfSortValues = it.data?.getArrayListOfSortingValues() ?: arrayListOf()
                    _items.postValue(ResponseState.Success(data = it.data))
                } else {
                    _items.postValue(it)

                }
            }
        }
    }

    fun applyChangesToSortList(position: Int) {
        for (index in 0 until listOfSortValues.size) {
            if (index != position) {
                listOfSortValues[index] = SortValueItem(
                    sortingValuesEnum = listOfSortValues[index].sortingValuesEnum,
                    isSelected = false,
                    name = listOfSortValues[index].name
                )
            }
        }

        filterTag = listOfSortValues[position].sortingValuesEnum

        listOfSortValues[position] =
            SortValueItem(
                sortingValuesEnum = listOfSortValues[position].sortingValuesEnum,
                isSelected = true,
                listOfSortValues[position].name
            )
    }

    fun filterList(): ArrayList<Restaurant> {
        Log.i(TAG, "filterList: tag : $filterTag")

        val finalList = ArrayList<Restaurant>()

        val favorites = listOfRestaurants.filter { it.isFavorite } as ArrayList

        val unFavorites = listOfRestaurants.filter { !it.isFavorite } as ArrayList

        when (filterTag) {
            Restaurant.SortingValuesEnum.BEST_MATCH -> {
                favorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.bestMatch })
                unFavorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.bestMatch })

            }
            Restaurant.SortingValuesEnum.AVERAGE_PRICE -> {
                favorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.averageProductPrice })
                unFavorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.averageProductPrice })
            }

            Restaurant.SortingValuesEnum.DELIVERY_COST -> {
                favorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenBy { it.sortingValues.deliveryCosts })
                unFavorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenBy { it.sortingValues.deliveryCosts })
            }

            Restaurant.SortingValuesEnum.DISTANCE -> {
                favorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenBy { it.sortingValues.distance })
                unFavorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenBy { it.sortingValues.distance })
            }

            Restaurant.SortingValuesEnum.MIN_COST -> {
                favorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenBy { it.sortingValues.minCost })
                unFavorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenBy { it.sortingValues.minCost })
            }

            Restaurant.SortingValuesEnum.NEW -> {
                favorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.newest })
                unFavorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.newest })
            }

            Restaurant.SortingValuesEnum.POPULAR -> {
                favorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.popularity })
                unFavorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.popularity })
            }

            Restaurant.SortingValuesEnum.RATE -> {
                favorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.ratingAverage })
                unFavorites.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.ratingAverage })
            }

            else -> {}
        }
        Log.i(TAG, "filterList: favorites : $favorites")
        Log.i(TAG, "filterList: un fave : $unFavorites")
        finalList.addAll(favorites)
        finalList.addAll(unFavorites)

        listOfRestaurants = finalList

        return listOfRestaurants

    }

    override fun onCleared() {
        Log.i(TAG, "onCleared: ")
        super.onCleared()
    }

    fun updateModel(boolean: Boolean, id: String) {
        viewModelScope.launch {
            Log.i(TAG, "updateModel: is favorite : ")
            repository.updateItem(boolean, id)

        }
    }
}