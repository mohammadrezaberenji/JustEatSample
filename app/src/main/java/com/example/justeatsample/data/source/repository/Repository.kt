package com.example.justeatsample.data.source.repository

import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.data.source.local_models.MenuItemsEntity
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getList(): Flow<ResponseState<MenuItemsEntity>>

    suspend fun updateItem(boolean: Boolean, id: String)
}