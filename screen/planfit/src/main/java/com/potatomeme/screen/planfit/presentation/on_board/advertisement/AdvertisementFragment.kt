package com.potatomeme.screen.planfit.presentation.on_board.advertisement

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.potatomeme.screen.planfit.databinding.FragmentAdvertisementBinding
import com.potatomeme.screen.planfit.databinding.FragmentLoadingRoutineBinding
import com.potatomeme.screen.planfit.presentation.on_board.PlanfitOnBoardingActivity
import com.potatomeme.screen.planfit.presentation.on_board.loading_routine.LoadingRoutineViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AdvertisementFragment : Fragment() {
    private lateinit var binding: FragmentAdvertisementBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAdvertisementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivClose.setOnClickListener {
            (activity as? PlanfitOnBoardingActivity)?.moveNextActivity()
        }
        binding.btnSubscribe.setOnClickListener {
            //todo 결제관련코드 playstore 등
        }
    }
}