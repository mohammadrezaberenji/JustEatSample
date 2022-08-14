package com.example.justeatsample.data.source.remote.webService

import com.example.justeatsample.data.source.ResponseState
import com.example.justeatsample.data.source.remote.apiModel.MenuResp
import retrofit2.Response
import retrofit2.http.GET

interface WebService {

    @GET("a0e248fc-0f10-4184-8782-f2e4f9406e3b")
    suspend fun getList(): Response<MenuResp>

}