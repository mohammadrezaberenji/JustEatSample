package com.example.justeatsample.data.source.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.local_models.SortingValues
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeRepository : Repository {

    private val _items = MutableLiveData<ResponseState<MenuItemsEntity>>()
    val items: LiveData<ResponseState<MenuItemsEntity>> = _items

    private val arrayList = arrayListOf(
        Restaurant(
            name = "test",
            sortingValues = SortingValues(
                averageProductPrice = 1,
                bestMatch = 2,
                deliveryCosts = 3,
                distance = 4,
                minCost = 50,
                newest = 10,
                popularity = 4,
                ratingAverage = 5.5
            ), status = "open",
            imageUrl = "https://test.com",
            id = "1",
            isFavorite = false
        ),
        Restaurant(
            name = "otherTest",
            sortingValues = SortingValues(
                averageProductPrice = 4,
                bestMatch = 2,
                deliveryCosts = 5,
                distance = 4,
                minCost = 100,
                newest = 100,
                popularity = 4,
                ratingAverage = 5.5
            ), status = "close",
            imageUrl = "https://test.com",
            id = "2",
            isFavorite = false
        ),
        Restaurant(
            name = "otherTest",
            sortingValues = SortingValues(
                averageProductPrice = 4,
                bestMatch = 2,
                deliveryCosts = 5,
                distance = 4,
                minCost = 100,
                newest = 100,
                popularity = 4,
                ratingAverage = 5.5
            ), status = "close",
            imageUrl = "https://test.com",
            id = "3",
            isFavorite = true
        )
    )

    override suspend fun getList(): Flow<ResponseState<MenuItemsEntity>> {
        return flow {
            ResponseState.Success(
                MenuItemsEntity(
                    restaurants = arrayList
                )
            )
        }
    }

    override suspend fun updateItem(boolean: Boolean, id: String) {
        arrayList.find { it.id == id }?.isFavorite = boolean

    }
}