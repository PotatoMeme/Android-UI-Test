package com.potatomeme.screen.planfit.presentation.on_board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.potatomeme.screen.planfit.data.model.Gym
import com.potatomeme.screen.planfit.databinding.ModalGymCheckBinding

class GymCheckBottomSheet(
    private val gym:Gym,
    private val onConfirm:(gym:Gym)->Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding : ModalGymCheckBinding

    companion object {
        const val TAG = "GymCheckBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = ModalGymCheckBinding.inflate(inflater, container, false)
        return binding.root.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            tvGymName.text = gym.name
            tvRoadName.text = gym.roadName
            tvPostNum.text = gym.postNum
            btnResearch.setOnClickListener {
                dismiss()
            }
            btnConfirm.setOnClickListener {
                onConfirm(gym)
                dismiss()
            }
        }

    }
}