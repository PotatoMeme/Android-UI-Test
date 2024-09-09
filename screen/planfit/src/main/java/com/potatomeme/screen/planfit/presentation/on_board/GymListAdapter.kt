package com.potatomeme.screen.planfit.presentation.on_board

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.potatomeme.screen.planfit.data.model.Gym
import com.potatomeme.screen.planfit.databinding.ItemGymBinding

class GymListAdapter(
    private val onGymClick: (Gym) -> Unit,
) : ListAdapter<Gym, GymListAdapter.GymViewHolder>(diffUtil) {

    inner class GymViewHolder(private val binding: ItemGymBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gym: Gym) {
            binding.apply {
                root.setOnClickListener {
                    onGymClick(gym)
                }
                tvGymName.text = gym.name
                tvRoadName.text = gym.roadName
                tvPostNum.text = gym.postNum
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GymViewHolder {
        val binding = ItemGymBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GymViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GymViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil: DiffUtil.ItemCallback<Gym> = object : DiffUtil.ItemCallback<Gym>() {
            override fun areItemsTheSame(oldItem: Gym, newItem: Gym): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Gym, newItem: Gym): Boolean {
                return oldItem == newItem
            }
        }
    }
}