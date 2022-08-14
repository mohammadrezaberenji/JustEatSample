package com.example.justeatsample.data.source.repository

import android.util.Log
import com.example.justeatsample.data.db.Dao
import com.example.justeatsample.data.db.MenuItemDbModel
import com.example.justeatsample.data.extensions.toMenuItemsEntity
import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.local_models.SortingValues
import com.example.justeatsample.data.source.remote.apiModel.MenuResp
import com.example.justeatsample.data.source.remote.webService.WebService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(private val webService: WebService, private val dao: Dao) {

    private val TAG = Repository::class.java.simpleName

    fun getList() = flow<ResponseState<MenuItemsEntity>> {
        try {
            emit(ResponseState.Loading)
            if (getItemsFromDb().isNotEmpty()) {
                Log.i(TAG, "getList: if dao is not empty ")
                emit(ResponseState.Success(MenuItemsEntity(getItemsFromDb())))
            } else {
                val response = webService.getList()
                if (response.isSuccessful) {
                    getItemsFromListAndInsertInDataBase(
                        response.body()?.toMenuItemsEntity()?.restaurants ?: arrayListOf()
                    )
                    emit(ResponseState.Success(response.body()?.toMenuItemsEntity()))
                } else {
                    emit(ResponseState.Error())
                }
            }

        } catch (e: Exception) {
            Log.e("TAG", "getList: exception : ${e.toString()} ")
            e.printStackTrace()
            emit(ResponseState.Error())
        }
    }


    private suspend fun getItemsFromListAndInsertInDataBase(arrayList: ArrayList<Restaurant>) {
        for (items in arrayList) {
            dao.insert(
                MenuItemDbModel(
                    name = items.name,
                    status = items.status,
                    imageUrl = items.imageUrl,
                    isFavorite = false,
                    averageProductPrice = items.sortingValues.averageProductPrice,
                    bestMatch = items.sortingValues.bestMatch,
                    distance = items.sortingValues.distance,
                    minCost = items.sortingValues.minCost,
                    newest = items.sortingValues.newest,
                    popularity = items.sortingValues.popularity,
                    ratingAverage = items.sortingValues.ratingAverage,
                    deliveryCosts = items.sortingValues.deliveryCosts
                )
            )
        }

    }

    private suspend fun getItemsFromDb(): ArrayList<Restaurant> {
        if (dao.getAllMenuItems().isNotEmpty()) {
            val list = ArrayList<Restaurant>()
            for (items in dao.getAllMenuItems()) {
                list.add(
                    Restaurant(
                        name = items.name,
                        status = items.status,
                        isFavorite = items.isFavorite,
                        imageUrl = items.imageUrl,
                        sortingValues = SortingValues(
                            averageProductPrice = items.averageProductPrice,
                            bestMatch = items.bestMatch,
                            deliveryCosts = items.deliveryCosts,
                            distance = items.distance,
                            minCost = items.minCost,
                            newest = items.newest,
                            popularity = items.popularity,
                            ratingAverage = items.ratingAverage
                        )
                    )
                )
            }

            return list

        } else {
            return arrayListOf()
        }
    }

    suspend fun updateItem(restaurant: Restaurant) {
        Log.i(TAG, "updateItem: ")
        val model = MenuItemDbModel(
            name = restaurant.name, status = restaurant.status,
            imageUrl = restaurant.imageUrl,
            isFavorite = restaurant.isFavorite,
            averageProductPrice = restaurant.sortingValues.averageProductPrice,
            bestMatch = restaurant.sortingValues.bestMatch,
            deliveryCosts = restaurant.sortingValues.deliveryCosts,
            distance = restaurant.sortingValues.distance,
            minCost = restaurant.sortingValues.minCost,
            newest = restaurant.sortingValues.newest,
            popularity = restaurant.sortingValues.popularity,
            ratingAverage = restaurant.sortingValues.ratingAverage
        )
        dao.update(model)

    }


}