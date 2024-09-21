package com.potatomeme.ticket_booking_app.presentation.ui.seat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ViewholderDateBinding

class DateAdapter(
    onItemClick: (Int) -> Unit,
) : BaseAdapter<String>(onItemClick) {

    inner class DateViewHolder(private val binding: ViewholderDateBinding) :
        BaseViewHolder<String>(binding.root) {

        override fun bind(date: String, isSelected: Boolean) {
            val dateParts = date.split("/")
            if (dateParts.size == 3) {
                binding.dayTxt.text = dateParts[0]
                binding.datMonthTxt.text = "${dateParts[1]} ${dateParts[2]}"
                if (isSelected) {
                    binding.mailLayout.setBackgroundResource(R.drawable.ic_white)
                    val textColor = ContextCompat.getColor(binding.root.context, R.color.black)
                    binding.dayTxt.setTextColor(textColor)
                    binding.datMonthTxt.setTextColor(textColor)
                } else {
                    binding.mailLayout.setBackgroundResource(R.drawable.ic_light_black)
                    val textColor = ContextCompat.getColor(binding.root.context, R.color.white)
                    binding.dayTxt.setTextColor(textColor)
                    binding.datMonthTxt.setTextColor(textColor)
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

    override fun onBindViewHolder(holder: BaseViewHolder<String>, position: Int) {
        holder.bind(items[position], position == selectedPosition)
    }
}