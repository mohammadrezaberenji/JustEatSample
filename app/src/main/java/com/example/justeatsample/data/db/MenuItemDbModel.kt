package com.example.justeatsample.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import com.example.justeatsample.data.source.local_models.SortingValues


@Entity
data class MenuItemDbModel(
    val name: String,
    val status: String,
    val imageUrl : String,
    val isFavorite : Boolean,
    val averageProductPrice: Int,
    val bestMatch: Int,
    val deliveryCosts: Int,
    val distance: Int,
    val minCost: Int,
    val newest: Int,
    val popularity: Int,
    val ratingAverage: Double

){
    @PrimaryKey(autoGenerate = true) var id: Int = 0

}