package com.nhlynn.paging_mvvm_hilt_coroutine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nhlynn.paging_mvvm_hilt_coroutine.databinding.PhotoItemBinding
import com.nhlynn.paging_mvvm_hilt_coroutine.delegate.PhotoDelegate
import com.nhlynn.paging_mvvm_hilt_coroutine.model.PhotoVO

class PhotoAdapter(private val mDelegate: PhotoDelegate) :
    PagingDataAdapter<PhotoVO, PhotoAdapter.PhotoViewHolder>(DIFF_UTIL) {

    class PhotoViewHolder(val viewBinding: PhotoItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root)

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<PhotoVO>() {
            override fun areItemsTheSame(oldItem: PhotoVO, newItem: PhotoVO): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PhotoVO, newItem: PhotoVO): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val viewHolder = holder.viewBinding
        val item = getItem(position) ?: return

        viewHolder.apply {
            if (item.urls != null) {
                Glide.with(root).load(item.urls!!.regular)
                    .into(ivPhoto)
            }

            tvName.text = item.description

            root.setOnClickListener {
                mDelegate.onViewPhoto(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding = PhotoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }
}