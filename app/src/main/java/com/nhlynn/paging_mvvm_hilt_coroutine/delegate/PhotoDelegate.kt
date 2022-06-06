package com.nhlynn.paging_mvvm_hilt_coroutine.delegate

import com.nhlynn.paging_mvvm_hilt_coroutine.model.PhotoVO

interface PhotoDelegate {
    fun onViewPhoto(photo: PhotoVO)
}