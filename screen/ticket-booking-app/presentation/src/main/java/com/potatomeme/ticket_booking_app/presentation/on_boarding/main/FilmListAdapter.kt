package com.potatomeme.ticket_booking_app.presentation.on_boarding.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.presentation.databinding.ViewholderFilmBinding

class FilmListAdapter(
    private val onItemClicked: (FilmEntity) -> Unit,
) : ListAdapter<FilmEntity, FilmListAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ViewholderFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(film: FilmEntity) {
            binding.tvName.text = film.title
            val requestOptions = RequestOptions()
                .transform(CenterCrop(), RoundedCorners(30))

            Glide.with(binding.root)
                .load(film.poster)
                .apply(requestOptions)
                .into(binding.ivPic)

            binding.root.setOnClickListener {
                onItemClicked(film)
            }
        }
    }

    companion object {
        private val diffCallback: DiffUtil.ItemCallback<FilmEntity> =
            object : DiffUtil.ItemCallback<FilmEntity>() {
                override fun areItemsTheSame(oldItem: FilmEntity, newItem: FilmEntity): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: FilmEntity, newItem: FilmEntity): Boolean =
                    oldItem == newItem
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderFilmBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}