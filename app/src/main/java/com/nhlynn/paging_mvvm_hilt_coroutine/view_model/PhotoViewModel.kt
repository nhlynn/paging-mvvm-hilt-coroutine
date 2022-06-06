package com.nhlynn.paging_mvvm_hilt_coroutine.view_model

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.nhlynn.paging_mvvm_hilt_coroutine.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel
@Inject constructor(
    private val repository: PhotoRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = state.getLiveData(QUERY, DEFAULT_QUERY)

    val filter = currentQuery.switchMap { query ->
        repository.getResult(query).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val QUERY = "query"
        private const val DEFAULT_QUERY = "butterfly"
    }
}