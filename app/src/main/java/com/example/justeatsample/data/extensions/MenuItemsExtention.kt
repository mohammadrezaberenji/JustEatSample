package com.example.justeatsample.data.extensions

import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.local_models.SortingValues
import com.example.justeatsample.data.source.remote.apiModel.MenuResp
import com.example.justeatsample.data.source.remote.apiModel.RestaurantResp
import com.example.justeatsample.data.source.remote.apiModel.SortingValuesResp


fun MenuResp.toMenuItemsEntity(): MenuItemsEntity {
    val list = ArrayList<Restaurant>()

    for (items in this.restaurants) {
        list.add(
            Restaurant(
                name = items.name,
                sortingValues = items.sortingValues.toSortingValues(),
                status = items.status,
                imageUrl = items.imageUrl,
                id = items.id,
                isFavorite = false
            )
        )
    }
    return MenuItemsEntity(list)
}

fun SortingValuesResp.toSortingValues(): SortingValues {
    return SortingValues(
        averageProductPrice,
        bestMatch,
        deliveryCosts,
        distance,
        minCost,
        newest,
        popularity,
        ratingAverage
    )
}