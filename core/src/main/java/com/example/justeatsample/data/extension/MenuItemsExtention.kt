package com.example.justeatsample.data.extension

import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.Restaurant
import com.example.justeatsample.data.source.local_models.SortingValues
import com.example.justeatsample.data.source.remote.api_model.MenuResp
import com.example.justeatsample.data.source.remote.api_model.SortingValuesResp


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