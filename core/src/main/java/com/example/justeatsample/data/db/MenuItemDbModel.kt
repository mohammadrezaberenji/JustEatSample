package com.example.justeatsample.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class MenuItemDbModel(
    val name: String,
    val status: String,
    val imageUrl: String,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean,
    @ColumnInfo(name = "uuid")
    val uuid: String,
    val averageProductPrice: Int,
    val bestMatch: Int,
    val deliveryCosts: Int,
    val distance: Int,
    val minCost: Int,
    val newest: Int,
    val popularity: Int,
    val ratingAverage: Double

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}