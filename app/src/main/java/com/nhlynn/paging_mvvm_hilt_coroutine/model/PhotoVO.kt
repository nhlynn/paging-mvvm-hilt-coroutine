package com.nhlynn.paging_mvvm_hilt_coroutine.model

import com.google.gson.annotations.SerializedName

data class PhotoVO(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("alt_description")
    var description: String? = null,
    @SerializedName("urls")
    var urls: PhotoUrlVO? = null
)
