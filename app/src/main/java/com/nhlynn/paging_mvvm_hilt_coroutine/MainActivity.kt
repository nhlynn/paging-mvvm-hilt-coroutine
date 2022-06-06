package com.nhlynn.paging_mvvm_hilt_coroutine

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nhlynn.paging_mvvm_hilt_coroutine.adapter.LoaderStateAdapter
import com.nhlynn.paging_mvvm_hilt_coroutine.adapter.PhotoAdapter
import com.nhlynn.paging_mvvm_hilt_coroutine.databinding.ActivityMainBinding
import com.nhlynn.paging_mvvm_hilt_coroutine.delegate.PhotoDelegate
import com.nhlynn.paging_mvvm_hilt_coroutine.model.PhotoVO
import com.nhlynn.paging_mvvm_hilt_coroutine.view_model.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PhotoDelegate {
    private lateinit var binding: ActivityMainBinding

    private val mPhotoViewModel by viewModels<PhotoViewModel>()

    private lateinit var mPhotoAdapter: PhotoAdapter

    private var keyword: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPhotoAdapter = PhotoAdapter(this)
        binding.rvPhoto.setHasFixedSize(true)
        binding.rvPhoto.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvPhoto.adapter = mPhotoAdapter.withLoadStateHeaderAndFooter(
            header = LoaderStateAdapter { mPhotoAdapter.retry() },
            footer = LoaderStateAdapter { mPhotoAdapter.retry() }
        )

        lifecycleScope.launch {
            mPhotoViewModel.filter.observe(this@MainActivity) {
                binding.apply {
                    progressBar.visibility = View.GONE
                    rvPhoto.visibility = View.VISIBLE
                }
                mPhotoAdapter.submitData(lifecycle, it)
            }
        }

        binding.edtSearch.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                keyword = binding.edtSearch.text.toString()
                if (!keyword.isNullOrEmpty()) {
                    closeKeyBoard()
                    binding.rvPhoto.scrollToPosition(0)
                    mPhotoViewModel.searchPhotos(keyword!!)
                }
                true
            } else {
                false
            }
        }

        mPhotoAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvPhoto.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    mPhotoAdapter.itemCount < 1
                ) {
                    rvPhoto.isVisible = false
                    tvEmpty.isVisible = true
                } else {
                    tvEmpty.isVisible = false
                }
            }
        }

        binding.btnRetry.setOnClickListener {
            mPhotoAdapter.retry()
        }
    }

    override fun onViewPhoto(photo: PhotoVO) {

    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}