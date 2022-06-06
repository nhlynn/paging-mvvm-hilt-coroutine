package com.nhlynn.paging_mvvm_hilt_coroutine.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(EndPoints.PHOTO)
    suspend fun getPhoto(
        @Query("page") page: Int,
        @Query("query") keyword: String
    ): PhotoResponse
}