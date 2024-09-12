package com.potatomeme.screen.planfit.presentation.on_board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
                viewModel.state.collect {
                    when (it) {
                        in SelectInfoState.STATE_EXERCISE_LEVEL..SelectInfoState.STATE_ROUTE -> {
                            binding.pbSelectinfo.progress = (it * 12.5).toInt()
                        }

                        SelectInfoState.STATE_COMPLETE -> {
                            //TODO: 다음 화면으로 이동
                        }
                    }
                }
            }
        }
    }
}