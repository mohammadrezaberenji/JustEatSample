package com.example.justeatsample.data.source.local_models

data class MenuItemsEntity(
    val restaurants: ArrayList<Restaurant>
)

data class Restaurant(
    val name: String,
    val sortingValues: SortingValues,
    val status: String,
    val imageUrl : String,
    val isFavorite : Boolean
)

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