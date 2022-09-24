package com.example.justeatsample.domain

import android.nfc.Tag
import com.example.justeatsample.ResponseState
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.local_models.SortValueItem
import com.example.justeatsample.data.source.repository.RepositoryImp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetMenuUseCase @Inject constructor(private val repositoryImp: RepositoryImp) {


    private var listOfRestaurants = ArrayList<Restaurant>()
    private var listOfSortValues = ArrayList<SortValueItem>()
    private var filterTag: Restaurant.SortingValuesEnum? = null


    suspend fun getMenu() = flow<ResponseState<ArrayList<Restaurant>>> {
        repositoryImp.getList().collect {
            listOfRestaurants = it.data?.getSortedList() ?: arrayListOf()
            listOfSortValues = it.data?.getArrayListOfSortingValues() ?: arrayListOf()
            emit(ResponseState.Success(listOfRestaurants))
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
}