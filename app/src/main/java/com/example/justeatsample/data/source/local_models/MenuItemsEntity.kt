package com.example.justeatsample.data.source.local_models

import android.util.Log

data class MenuItemsEntity(
    val restaurants: ArrayList<Restaurant>
) {
    fun getSortedList(): ArrayList<Restaurant> {
        val finalList = ArrayList<Restaurant>()

        val list = restaurants.filter { it.isFavorite } as ArrayList
        list.sortBy { it.getStatus() }

        val others = restaurants.filter { !it.isFavorite } as ArrayList
        others.sortBy { it.getStatus() }
        if (list.isNotEmpty())
            finalList.addAll(list)
        if (others.isNotEmpty())
            finalList.addAll(others)
        Log.i("TAG", "getSortedList: other : final list :${finalList.size}")

        return finalList
    }
}

data class Restaurant(
    var name: String,
    val sortingValues: SortingValues,
    val status: String,
    val imageUrl: String,
    val id : String,
    var isFavorite: Boolean
) {
    enum class Status(val status: String) {
        OPEN("open"), CLOSED("closed"), ORDER_AHEAD("order ahead")

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
)