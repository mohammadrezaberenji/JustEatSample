package com.example.justeatsample.data.source.local_models

import android.util.Log
import java.io.Serializable

data class MenuItemsEntity(
    val restaurants: ArrayList<Restaurant>
)

data class Restaurant(
    var name: String,
    val sortingValues: SortingValues,
    val status: String,
    val imageUrl: String,
    val id: String,
    var isFavorite: Boolean
) : Serializable {
    enum class Status(val status: String) {
        OPEN("open"), CLOSED("closed"), ORDER_AHEAD("order ahead")

    }

    enum class SortingValuesEnum {
        AVERAGE_PRICE, BEST_MATCH, DELIVERY_COST, DISTANCE, MIN_COST, NEW, POPULAR, RATE, RESET
    }

    fun getStatus(): Int {
        return when (status) {
            "open" -> 0;
            "order ahead" -> 1
            "closed" -> 2
            else -> 3
        }
    }


}

data class SortingValues(
    val averageProductPrice: Int,
    val bestMatch: Int,
    val deliveryCosts: Int,
    val distance: Int,
    val minCost: Int,
    val newest: Int,
    val popularity: Int,
    val ratingAverage: Double
) : Serializable