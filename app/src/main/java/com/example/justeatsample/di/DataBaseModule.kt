package com.example.justeatsample.di

import android.content.Context
import androidx.room.Room
import com.example.justeatsample.data.db.Dao
import com.example.justeatsample.data.db.DataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Provides
    fun provideDao(dataBase: DataBase): Dao {
        return dataBase.dataBaseDao()
    }


    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext applicationContext: Context): DataBase {
        return Room.databaseBuilder(applicationContext, DataBase::class.java, "RssReader").build()
    }
}