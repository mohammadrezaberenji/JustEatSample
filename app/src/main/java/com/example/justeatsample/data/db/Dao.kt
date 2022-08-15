package com.example.justeatsample.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.justeatsample.data.source.local_models.MenuItemsEntity

@Dao
interface Dao {

    @Query("SELECT * FROM menuitemdbmodel")
    suspend fun getAllMenuItems(): List<MenuItemDbModel>

    @Insert
    suspend fun insert(menuItemDbModel: MenuItemDbModel)

    @Query("UPDATE menuitemdbmodel SET isFavorite =:isFavorite WHERE id =:id")
    suspend fun update(isFavorite: Boolean, id: Int)
}