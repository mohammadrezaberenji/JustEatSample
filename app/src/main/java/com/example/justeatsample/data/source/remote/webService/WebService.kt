package com.example.justeatsample.data.source.remote.webService

import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.data.source.remote.apiModel.MenuResp
import retrofit2.Response
import retrofit2.http.GET

interface WebService {

    @GET("4f3f95f7-e227-4831-b484-bc9058a3a9a7")
    suspend fun getList(): Response<MenuResp>

}