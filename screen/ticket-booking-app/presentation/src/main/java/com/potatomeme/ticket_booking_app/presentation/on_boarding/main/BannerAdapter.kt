package com.potatomeme.ticket_booking_app.presentation.on_boarding.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.potatomeme.ticket_booking_app.domain.entity.BannerEntity
import com.potatomeme.ticket_booking_app.presentation.databinding.ViewholderBannerBinding

class BannerAdapter : ListAdapter<BannerEntity, BannerAdapter.BannerViewHolder>(diffCallback) {

    inner class BannerViewHolder(private val binding: ViewholderBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bannerEntity: BannerEntity) {
            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(60))
            Glide.with(binding.root)
                .load(bannerEntity.image)
                .apply(requestOptions)
                .into(binding.imageSlide)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BannerViewHolder {
        val binding =
            ViewholderBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {
        private val diffCallback: DiffUtil.ItemCallback<BannerEntity> =
            object : DiffUtil.ItemCallback<BannerEntity>() {
                override fun areItemsTheSame(
                    oldItem: BannerEntity,
                    newItem: BannerEntity,
                ): Boolean = oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: BannerEntity,
                    newItem: BannerEntity,
                ): Boolean = oldItem == newItem
            }
    }
}