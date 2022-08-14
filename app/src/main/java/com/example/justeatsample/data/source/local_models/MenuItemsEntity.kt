package com.example.justeatsample.data.source.local_models

data class MenuItemsEntity(
    val restaurants: ArrayList<Restaurant>
)

data class Restaurant(
    var name: String,
    val sortingValues: SortingValues,
    val status: String,
    val imageUrl: String,
    var isFavorite: Boolean
) {
    enum class Status(val status: String) {
        OPEN("open"), CLOSED("closed"), ORDER_AHEAD("order ahead")

    }

    fun getStatus(): Int {
        return when (status) {
            "open" -> 2;
            "order ahead" -> 1
            "closed" -> 0
            else -> -1
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