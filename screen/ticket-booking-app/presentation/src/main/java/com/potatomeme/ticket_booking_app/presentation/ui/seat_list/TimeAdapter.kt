package com.potatomeme.ticket_booking_app.presentation.ui.seat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ViewholderTimeBinding

class TimeAdapter(
    onItemClick: (Int) -> Unit,
) : BaseAdapter<String>(onItemClick) {

    inner class TimeViewHolder(private val binding: ViewholderTimeBinding) :
        BaseViewHolder<String>(binding.root) {

        override fun bind(time: String, isSelected: Boolean) {
            binding.TextViewTime.text = time
            if (isSelected) {
                binding.TextViewTime.setBackgroundResource(R.drawable.ic_white)
                val textColor = ContextCompat.getColor(itemView.context, R.color.black)
                binding.TextViewTime.setTextColor(textColor)
            } else {
                binding.TextViewTime.setBackgroundResource(R.drawable.ic_light_black)
                val textColor = ContextCompat.getColor(itemView.context, R.color.white)
                binding.TextViewTime.setTextColor(textColor)
            }

            binding.root.setOnClickListener {
                if (!isSelected) {
                    onItemClick(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeAdapter.TimeViewHolder {
        return TimeViewHolder(
            ViewholderTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<String>, position: Int) {
        holder.bind(items[position], position == selectedPosition)
    }
}