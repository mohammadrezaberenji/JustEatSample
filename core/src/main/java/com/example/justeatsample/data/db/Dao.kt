package com.example.justeatsample.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface Dao {

    @Query("SELECT * FROM menuitemdbmodel")
    suspend fun getAllMenuItems(): List<MenuItemDbModel>

    @Insert
    suspend fun insert(menuItemDbModel: MenuItemDbModel)

    @Query("UPDATE menuitemdbmodel SET isFavorite =:isFavorite WHERE uuid =:id")
    suspend fun update(isFavorite: Boolean, id: String)
}