package com.example.justeatsample.data.source.local_models

import android.util.Log

data class MenuItemsEntity(
    val restaurants: ArrayList<Restaurant>
) {
    fun getSortedList(): ArrayList<Restaurant> {
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

data class Restaurant(
    var name: String,
    val sortingValues: SortingValues,
    val status: String,
    val imageUrl: String,
    val id: String,
    var isFavorite: Boolean
) {
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
)