package com.nhlynn.paging_mvvm_hilt_coroutine.model

import com.google.gson.annotations.SerializedName

data class PhotoUrlVO(
    @SerializedName("raw")
    val raw: String,
    @SerializedName("full")
    val full: String,
    @SerializedName("regular")
    val regular: String,
    @SerializedName("small")
    val small: String,
    @SerializedName("thumb")
    val thumb: String
)