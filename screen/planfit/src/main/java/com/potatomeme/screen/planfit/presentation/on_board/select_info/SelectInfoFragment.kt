package com.potatomeme.screen.planfit.presentation.on_board.select_info

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.potatomeme.screen.planfit.R
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectInfoFragment : Fragment() {
    private lateinit var binding: FragmentSelectinfoBinding
    private val viewModel: SelectInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSelectinfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        in SelectInfoState.STATE_EXERCISE_LEVEL..SelectInfoState.STATE_ROUTE -> {
                            val newProgress = (state * 12.5).toInt()
                            animateProgress(binding.pbSelectinfo.progress, newProgress)
                        }

                        SelectInfoState.STATE_COMPLETE -> {
                            //TODO: 다음 화면으로 이동
                            viewModel.postUserInfo()
                            viewModel.setState(SelectInfoState.STATE_ROUTE)
                            findNavController().navigate(R.id.action_selectInfoFragment_to_selectInfoNotifiationFragment)
                        }
                    }
                    binding.ivBack.setOnClickListener{
                        requireActivity().onBackPressed()
                    }
                }
            }
        }
    }

    private fun animateProgress(startProgress: Int, endProgress: Int) {
        ObjectAnimator.ofInt(binding.pbSelectinfo, "progress", startProgress, endProgress).apply {
            duration = 500
            start()
        }
    }
}