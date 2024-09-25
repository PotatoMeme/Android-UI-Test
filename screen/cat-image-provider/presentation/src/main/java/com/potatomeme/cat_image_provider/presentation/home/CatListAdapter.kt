package com.potatomeme.cat_image_provider.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.potatomeme.cat_image_provider.presentation.databinding.ItemCatBinding
import com.potatomeme.cat_image_provider.presentation.model.ParcelableCat

class CatListAdapter(private val onItemClick: (ParcelableCat) -> Unit) :
    ListAdapter<ParcelableCat, CatListAdapter.CatViewHolder>(diffCallback) {
    inner class CatViewHolder(private val binding: ItemCatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ParcelableCat) {
            val requestOptions = RequestOptions().transform(RoundedCorners(60))
            Glide.with(binding.imageView)
                .load(item.url)
                .apply(requestOptions)
                .into(binding.imageView)

            binding.imageView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ParcelableCat>() {
            override fun areItemsTheSame(oldItem: ParcelableCat, newItem: ParcelableCat): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ParcelableCat,
                newItem: ParcelableCat,
            ): Boolean {
                return oldItem == newItem
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val binding = ItemCatBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(item = getItem(position))
    }
}

