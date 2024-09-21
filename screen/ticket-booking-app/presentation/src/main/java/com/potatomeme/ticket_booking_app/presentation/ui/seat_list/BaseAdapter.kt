package com.potatomeme.ticket_booking_app.presentation.ui.seat_list

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T>(
    protected val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<BaseAdapter.BaseViewHolder<T>>() {
    protected val items = mutableListOf<T>()
    protected var selectedPosition = RecyclerView.NO_POSITION
    private var lastSelectedPosition = RecyclerView.NO_POSITION

    fun submitList(newList: List<T>) {
        items.clear()
        items.addAll(newList)
        selectedPosition = 0
        lastSelectedPosition = 0
        notifyDataSetChanged()
    }

    fun updateSelected(lastPosition: Int, selectPosition: Int) {
        lastSelectedPosition = lastPosition
        selectedPosition = selectPosition
        notifyItemChanged(lastSelectedPosition)
        notifyItemChanged(selectedPosition)
    }

    override fun getItemCount(): Int = items.size

    abstract class BaseViewHolder<T>(bindingRoot: View) : RecyclerView.ViewHolder(bindingRoot) {
        abstract fun bind(item: T, isSelected: Boolean)
    }
}