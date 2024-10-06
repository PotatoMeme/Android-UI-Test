package com.potatomeme.screen.planfit.presentation.on_board.loading_routine

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.potatomeme.screen.planfit.R
import com.potatomeme.screen.planfit.databinding.FragmentLoadingRoutineBinding
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoBinding
import com.potatomeme.screen.planfit.presentation.on_board.select_info.SelectInfoState
import com.potatomeme.screen.planfit.presentation.on_board.select_info.SelectInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadingRoutineFragment : Fragment() {
    private lateinit var binding: FragmentLoadingRoutineBinding
    private val viewModel: LoadingRoutineViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoadingRoutineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.firstStepCorrect.collect {
                        if (it) {
                            binding.iv1stStepCorrect.isSelected = true
                            animateProgress(0, 25)
                        }
                    }
                }
                launch {
                    viewModel.secondStepCorrect.collect {
                        if (it) {
                            binding.iv2ndStepCorrect.isSelected = true
                            animateProgress(25, 50)
                        }
                    }
                }
                launch {
                    viewModel.thirdStepCorrect.collect {
                        if (it) {
                            binding.iv3rdStepCorrect.isSelected = true
                            animateProgress(50, 75)
                        }
                    }
                }
                launch {
                    viewModel.fourthStepCorrect.collect {
                        if (it) {
                            binding.iv4thStepCorrect.isSelected = true
                            animateProgress(75,100)
                            delay(500L)
                            //todo 다음 화면으로 이동
                        }
                    }
                }
            }
        }
        viewModel.startLoading()
    }

    private fun animateProgress(startProgress: Int, endProgress: Int) {
        ObjectAnimator.ofInt(binding.pbLoading, "progress", startProgress, endProgress).apply {
            duration = 500
            start()
        }
    }
}