package com.potatomeme.ticket_booking_app.presentation.ui.seat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ViewholderTimeBinding

class TimeAdapter(
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<TimeAdapter.TimeViewHolder>() {
    private val timeSlots = mutableListOf<String>()
    private var selectedPosition = RecyclerView.NO_POSITION
    private var lastSelectedPosition = RecyclerView.NO_POSITION

    fun submitList(newList: List<String>) {
        timeSlots.clear()
        timeSlots.addAll(newList)
        selectedPosition = 0
        lastSelectedPosition = 0
        notifyDataSetChanged()
    }

    fun updateSelected(lastPosition: Int, selectPosition: Int) {
        lastSelectedPosition = lastPosition
        selectedPosition = selectPosition
        if (lastSelectedPosition !in timeSlots.indices || selectedPosition !in timeSlots.indices) return
        notifyItemChanged(lastSelectedPosition)
        notifyItemChanged(selectedPosition)
    }

    inner class TimeViewHolder(private val binding: ViewholderTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(time: String, isSelected: Boolean) {
            binding.TextViewTime.text = time
            if (isSelected) {
                binding.TextViewTime.setBackgroundResource(R.drawable.ic_white)
                binding.TextViewTime.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.black
                    )
                )
            } else {
                binding.TextViewTime.setBackgroundResource(R.drawable.ic_light_black)
                binding.TextViewTime.setTextColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.white
                    )
                )
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

    override fun onBindViewHolder(holder: TimeAdapter.TimeViewHolder, position: Int) {
        holder.bind(timeSlots[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = timeSlots.size
}