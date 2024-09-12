package com.potatomeme.screen.planfit.presentation.on_board.select_info

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
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoBodygoalBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectInfoBodyGoalFragment : Fragment() {
    private lateinit var binding: FragmentSelectinfoBodygoalBinding
    private val viewModel: SelectInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSelectinfoBodygoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toggleList = listOf(
            binding.toggleGoal1,
            binding.toggleGoal2,
            binding.toggleGoal3,
            binding.toggleGoal4,
            binding.toggleGoal5,
            binding.toggleGoal6,
            binding.toggleGoal7,
            binding.toggleGoal8,
            binding.toggleGoal9,
            binding.toggleGoal10,
            binding.toggleGoal11,
            binding.toggleGoal12,
        )

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.infoBodyGoal.collect { list ->
                    var anyTrue = false
                    for (i in toggleList.indices) {
                        toggleList[i].isSelected = list[i]
                        anyTrue = anyTrue || list[i]
                    }
                    binding.btnNext.isEnabled = anyTrue
                }
            }
        }

        toggleList.forEachIndexed { index, view ->
            view.setOnClickListener {
                viewModel.updateSelectedBodyGoalListWithIndex(index)
            }
        }

        binding.btnNext.setOnClickListener {
            //TODO: 다음 화면으로 이동
            findNavController().navigate(R.id.action_selectInfoBodyGoalFragment_to_selectInfoSexFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setState(SelectInfoState.STATE_BODY_GOAL)
    }

}