package com.example.justeatsample.data.db

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [MenuItemDbModel::class], version = 4)
abstract class DataBase : RoomDatabase(){
    abstract fun dataBaseDao(): Dao
}