package com.potatomeme.ticket_booking_app.presentation.ui.seat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ViewholderDateBinding

class DateAdapter(
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {
    private val dateSlots = mutableListOf<String>()
    private var selectedPosition = RecyclerView.NO_POSITION
    private var lastSelectedPosition = RecyclerView.NO_POSITION

    fun submitList(newList: List<String>) {
        dateSlots.clear()
        dateSlots.addAll(newList)
        selectedPosition = 0
        lastSelectedPosition = 0
        notifyDataSetChanged()
    }

    fun updateSelected(lastPosition: Int, selectPosition: Int) {
        lastSelectedPosition = lastPosition
        selectedPosition = selectPosition
        if (lastSelectedPosition !in dateSlots.indices || selectedPosition !in dateSlots.indices) return
        notifyItemChanged(lastSelectedPosition)
        notifyItemChanged(selectedPosition)
    }

    inner class DateViewHolder(private val binding: ViewholderDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: String, isSelected: Boolean) {
            val dateParts = date.split("/")
            if (dateParts.size == 3) {
                binding.dayTxt.text = dateParts[0]
                binding.datMonthTxt.text = dateParts[1] + " " + dateParts[2]
                if (isSelected) {
                    binding.mailLayout.setBackgroundResource(R.drawable.ic_white)
                    binding.dayTxt.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.black
                        )
                    )
                    binding.datMonthTxt.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.black
                        )
                    )
                } else {
                    binding.mailLayout.setBackgroundResource(R.drawable.ic_light_black)
                    binding.dayTxt.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white
                        )
                    )
                    binding.datMonthTxt.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white
                        )
                    )
                }

                binding.root.setOnClickListener {
                    onItemClick(adapterPosition)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateAdapter.DateViewHolder {
        return DateViewHolder(
            ViewholderDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DateAdapter.DateViewHolder, position: Int) {
        holder.bind(dateSlots[position], position == selectedPosition)
    }

    override fun getItemCount(): Int = dateSlots.size
}