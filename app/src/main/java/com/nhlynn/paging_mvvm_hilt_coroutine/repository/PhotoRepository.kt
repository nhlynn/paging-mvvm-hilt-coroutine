package com.nhlynn.paging_mvvm_hilt_coroutine.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.nhlynn.paging_mvvm_hilt_coroutine.network.ApiService
import com.nhlynn.paging_mvvm_hilt_coroutine.pagination.PhotoPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository
@Inject constructor(private val apiService: ApiService) {
    fun getResult(keyword: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 200,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotoPagingSource(apiService, keyword) }
        ).liveData
}