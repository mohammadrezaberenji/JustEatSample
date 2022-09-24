package com.example.justeatsample.data.source.repository

import android.util.Log
import com.example.justeatsample.ResponseState
import com.example.justeatsample.data.db.Dao
import com.example.justeatsample.data.db.MenuItemDbModel
import com.example.justeatsample.data.extension.toMenuItemsEntity
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.local_models.SortingValues
import com.example.justeatsample.data.source.remote.webService.WebService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImp @Inject constructor(private val webService: WebService, private val dao: Dao) :
    Repository {

    private val TAG = RepositoryImp::class.java.simpleName

    private var list = ArrayList<MenuItemDbModel>()


    override suspend fun getList() = flow<ResponseState<MenuItemsEntity>> {
        try {
            emit(ResponseState.Loading)
            if (false) {
//                Log.i(TAG, "getList: if dao is not empty ")
                emit(ResponseState.Success(MenuItemsEntity(arrayListOf())))
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
//            Log.e("TAG", "getList: exception : ${e.toString()} ")
            e.printStackTrace()
            emit(ResponseState.Error())
        }
    }

//    fun getList() = flow<ResponseState<MenuItemsEntity>> {
//
//    }


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
                    deliveryCosts = items.sortingValues.deliveryCosts,
                    uuid = items.id
                )
            )
        }

    }

    suspend fun getItemsFromDb(): ArrayList<Restaurant> {
        list =
            dao.getAllMenuItems() as ArrayList<MenuItemDbModel> /* = java.util.ArrayList<com.example.justeatsample.data.db.MenuItemDbModel> */

        if (list.isNotEmpty()) {
            val list = ArrayList<Restaurant>()
            for (items in dao.getAllMenuItems()) {
                list.add(
                    Restaurant(
                        name = items.name,
                        status = items.status,
                        isFavorite = items.isFavorite,
                        imageUrl = items.imageUrl,
                        id = items.uuid,
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

    override suspend fun updateItem(boolean: Boolean, id: String) {
        Log.i(TAG, "updateItem:  ")
        dao.update(boolean, id)

    }


}