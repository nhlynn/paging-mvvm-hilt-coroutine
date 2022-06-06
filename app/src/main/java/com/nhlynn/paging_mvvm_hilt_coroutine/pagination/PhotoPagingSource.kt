package com.nhlynn.paging_mvvm_hilt_coroutine.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nhlynn.paging_mvvm_hilt_coroutine.model.PhotoVO
import com.nhlynn.paging_mvvm_hilt_coroutine.network.ApiService
import javax.inject.Inject

class PhotoPagingSource
@Inject constructor(private val apiService: ApiService, private val keyword: String) :
    PagingSource<Int, PhotoVO>() {
    override fun getRefreshKey(state: PagingState<Int, PhotoVO>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoVO> {
        val page = params.key ?: 1

        return try {

            val data = apiService.getPhoto(page, keyword)

            LoadResult.Page(
                data = data.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (data.results.isEmpty()) null else page + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}