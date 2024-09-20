package com.potatomeme.ticket_booking_app.presentation.ui.seat_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.ticket_booking_app.domain.entity.SeatEntity
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ViewholderSeatBinding

class SeatListAdapter(
    private val colorList: List<Int>,
    private val onItemClick: (Int, SeatEntity) -> Unit,
) : RecyclerView.Adapter<SeatListAdapter.SeatViewHolder>() {
    private val list =
        MutableList(81) { SeatEntity(SeatEntity.SeatStatus.AVAILABLE, "") }
    private val bgList = listOf(
        R.drawable.ic_seat_available,
        R.drawable.ic_seat_selected,
        R.drawable.ic_seat_unavailable
    )

    fun submitList(newList: List<SeatEntity>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun updatePosition(position: Int, newList: List<SeatEntity>) {
        list.clear()
        list.addAll(newList)
        notifyItemChanged(position)
    }

    inner class SeatViewHolder(private val binding: ViewholderSeatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(seat: SeatEntity, itemClick: (SeatEntity) -> Unit) {
            binding.seat.text = seat.name
            binding.seat.setBackgroundResource(bgList[seat.status.ordinal])
            binding.seat.setTextColor(colorList[seat.status.ordinal])

            binding.seat.setOnClickListener {
                when (seat.status) {
                    SeatEntity.SeatStatus.AVAILABLE -> {
                        itemClick(seat.copy(status = SeatEntity.SeatStatus.SELECTED))
                    }

                    SeatEntity.SeatStatus.SELECTED -> {
                        itemClick(seat.copy(status = SeatEntity.SeatStatus.AVAILABLE))
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SeatListAdapter.SeatViewHolder {
        val binding =
            ViewholderSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeatViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SeatListAdapter.SeatViewHolder, position: Int) {
        holder.bind(list[position]) { seat ->
            onItemClick(position, seat)
        }
    }

}