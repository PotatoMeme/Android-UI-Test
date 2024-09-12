package com.potatomeme.screen.planfit.presentation.on_board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.potatomeme.screen.planfit.data.model.Gym
import com.potatomeme.screen.planfit.databinding.ModalDatepickerBinding
import com.potatomeme.screen.planfit.databinding.ModalGymCheckBinding

class BirthPickerBottomSheet(
    private val year: Int,
    private val month: Int,
    private val day: Int,
    private val onConfirm: (Int, Int, Int) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding : ModalDatepickerBinding

    companion object {
        const val TAG = "BirthPickerBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = ModalDatepickerBinding.inflate(inflater, container, false)
        return binding.root.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.run {
            dpBirth.updateDate(year, month, day)

            btnConfirm.setOnClickListener {
                val year = dpBirth.year
                val month = dpBirth.month
                val day = dpBirth.dayOfMonth
                onConfirm(year, month, day)
                dismiss()
            }
        }

    }
}