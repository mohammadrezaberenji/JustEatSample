package com.example.justeatsample.data.source.remote.apiModel

data class MenuResp(
    val restaurants: ArrayList<RestaurantResp>
)

data class RestaurantResp(
    val name: String,
    val sortingValues: SortingValuesResp,
    val status: String,
    val id : String,
    val imageUrl : String
)

data class SortingValuesResp(
    val averageProductPrice: Int,
    var bestMatch: Int,
    val deliveryCosts: Int,
    val distance: Int,
    val minCost: Int,
    val newest: Int,
    val popularity: Int,
    val ratingAverage: Double
)