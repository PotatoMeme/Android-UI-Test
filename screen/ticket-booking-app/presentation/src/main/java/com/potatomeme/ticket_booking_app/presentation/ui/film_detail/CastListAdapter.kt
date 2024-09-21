package com.potatomeme.ticket_booking_app.presentation.ui.film_detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.potatomeme.ticket_booking_app.presentation.databinding.ViewholderCastBinding
import com.potatomeme.ticket_booking_app.presentation.model.ParcelableCast

class CastListAdapter(private val cast: List<ParcelableCast>) :
    RecyclerView.Adapter<CastListAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: ViewholderCastBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: ParcelableCast) {
            Glide.with(binding.root)
                .load(cast.picUrl)
                .into(binding.actorImage)
            binding.nameTxt.text = cast.actor
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastListAdapter.ViewHolder {
        val binding =
            ViewholderCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastListAdapter.ViewHolder, position: Int) {
        holder.bind(cast[position])
    }

    override fun getItemCount(): Int {
        return cast.size
    }

}