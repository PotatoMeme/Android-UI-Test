package com.potatomeme.screen.planfit.presentation.on_board.select_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.potatomeme.screen.planfit.R
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoNotificationBinding
import com.potatomeme.screen.planfit.presentation.on_board.PlanfitOnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectInfoNotificationFragment : Fragment() {
    private lateinit var binding: FragmentSelectinfoNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSelectinfoNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext.setOnClickListener {
            (activity as PlanfitOnBoardingActivity).requestNotificationPermission {
                //TODO: 다음 화면으로 이동
                view.findNavController().navigate(R.id.action_selectInfoNotificationFragment_to_selectInfoRoutineFragment)
            }
        }
    }
}