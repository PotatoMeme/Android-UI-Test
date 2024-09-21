package com.potatomeme.ticket_booking_app.presentation.ui.film_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.ticket_booking_app.presentation.databinding.ViewholderCategoryBinding

class CategoryEachFilmAdapter (private val items: List<String>) :
    RecyclerView.Adapter<CategoryEachFilmAdapter.ViewHolder>() {
    class ViewHolder(private val binding: ViewholderCategoryBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: String) {
            binding.titleTxt.text = item
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}