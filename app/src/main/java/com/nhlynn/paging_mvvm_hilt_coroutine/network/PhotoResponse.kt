package com.nhlynn.paging_mvvm_hilt_coroutine.network

import com.google.gson.annotations.SerializedName
import com.nhlynn.paging_mvvm_hilt_coroutine.model.PhotoVO

data class PhotoResponse(
    @SerializedName("results")
    var results: List<PhotoVO>
)
