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
import dagger.hilt.migration.DisableInstallInCheck
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDao(dataBase: DataBase): Dao {
        return dataBase.dataBaseDao()
    }


    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): DataBase {
        val db: DataBase = Room.databaseBuilder(
            context, DataBase::class.java, "database-name"
        ).build()
        return db
    }

//    @Singleton
//    @Provides
//    fun provideCharacterFavoriteRepository(
//        repository: RepositoryImp
//    ) = RepositoryImp()


//    private var INSTANCE: DataBase? = null

//    @JvmStatic
//    fun getInstance(context: Context): DataBase {
//        return INSTANCE ?: synchronized(this) {
//            INSTANCE ?: run {
//                val db = Room.databaseBuilder(
//                    context,
//                    DataBase::class.java, "database-name"
//                ).build()
//                DataBase().also { INSTANCE = it }
//            }
//        }
//
//    }
}