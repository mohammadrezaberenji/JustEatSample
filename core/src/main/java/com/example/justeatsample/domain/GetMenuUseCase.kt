package com.example.justeatsample.domain

import android.nfc.Tag
import android.util.Log
import com.example.justeatsample.ResponseState
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.local_models.SortValueItem
import com.example.justeatsample.data.source.repository.RepositoryImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.internal.ChannelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMenuUseCase @Inject constructor(private val repositoryImp: RepositoryImp) {


    private var listOfRestaurants = ArrayList<Restaurant>()
    private var listOfSortValues = ArrayList<SortValueItem>()
    private var filterTag: Restaurant.SortingValuesEnum? = null
    private val TAG = GetMenuUseCase::class.java.simpleName


    suspend fun getMenu() = flow<ResponseState<ArrayList<Restaurant>>> {
        Log.i(TAG, "getMenu: ")
        repositoryImp.getList().collect {
            Log.i(TAG, "getMenu: ${it.data}")
            if (it is ResponseState.Success) {
                listOfRestaurants = getSortedList(it.data?.restaurants ?: arrayListOf())
                listOfSortValues = getArrayListOfSortingValues()
                emit(ResponseState.Success(listOfRestaurants))

            }
        }


    }


    fun filterList(): ArrayList<Restaurant> {

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
        finalList.addAll(favorites)
        finalList.addAll(unFavorites)

        listOfRestaurants = finalList

        return listOfRestaurants

    }


    suspend fun updateModel(boolean: Boolean, id: String) {
        repositoryImp.updateItem(boolean, id)

    }

    fun applyChanges(position: Int) {
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


    fun getSortedList(restaurants: ArrayList<Restaurant>): ArrayList<Restaurant> {
        val finalList = ArrayList<Restaurant>()

        val list = restaurants.filter { it.isFavorite } as ArrayList
        list.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.bestMatch })

        val others = restaurants.filter { !it.isFavorite } as ArrayList
        others.sortWith(compareBy<Restaurant> { it.getStatus() }.thenByDescending { it.sortingValues.bestMatch })


        if (list.isNotEmpty())
            finalList.addAll(list)
        if (others.isNotEmpty())
            finalList.addAll(others)
        Log.i("TAG", "getSortedList: other : final list :${finalList.size}")

        return finalList
    }

    fun getArrayListOfSortingValues(): ArrayList<SortValueItem> {
        val list = ArrayList<SortValueItem>()
        list.add(
            SortValueItem(
                Restaurant.SortingValuesEnum.BEST_MATCH,
                name = "best match",
                isSelected = true
            )
        )
        list.add(
            SortValueItem(
                Restaurant.SortingValuesEnum.AVERAGE_PRICE,
                name = "average price",
                isSelected = false
            )
        )
        list.add(
            SortValueItem(
                Restaurant.SortingValuesEnum.DELIVERY_COST,
                name = "delivery costs",
                isSelected = false
            )
        )
        list.add(
            SortValueItem(
                Restaurant.SortingValuesEnum.DISTANCE,
                name = "distance",
                isSelected = false
            )
        )
        list.add(
            SortValueItem(
                Restaurant.SortingValuesEnum.MIN_COST,
                name = "min cost",
                isSelected = false
            )
        )
        list.add(
            SortValueItem(
                Restaurant.SortingValuesEnum.NEW,
                name = "newest",
                isSelected = false
            )
        )
        list.add(
            SortValueItem(
                Restaurant.SortingValuesEnum.POPULAR,
                name = "popularity",
                isSelected = false
            )
        )
        list.add(
            SortValueItem(
                Restaurant.SortingValuesEnum.RATE,
                name = "rating average",
                isSelected = false
            )
        )

        return list
    }
}