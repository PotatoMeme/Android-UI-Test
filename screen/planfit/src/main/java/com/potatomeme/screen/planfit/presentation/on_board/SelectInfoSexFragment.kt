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
import androidx.navigation.findNavController
import com.potatomeme.screen.planfit.R
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoEquipmentTypeBinding
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoExerciseLevelBinding
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoPlaceBinding
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoSexBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectInfoSexFragment : Fragment() {
    private lateinit var binding: FragmentSelectinfoSexBinding
    private val viewModel: SelectInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSelectinfoSexBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toggleList = listOf(
            binding.toggleMale,
            binding.toggleFemale,
            binding.toggleEtc
        )

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.infoSex.collect {
                    when (it) {
                        SelectInfo.None -> {
                            toggleList.forEach { it.isSelected = false }
                            binding.btnNext.isEnabled = false
                        }

                        is SelectInfo.SelectInfoSelected -> {
                            toggleList.forEach { it.isSelected = false }
                            toggleList[it.level].isSelected = true
                            binding.btnNext.isEnabled = true
                        }
                    }
                }
            }
        }

        toggleList.forEachIndexed { index, view ->
            view.setOnClickListener {
                viewModel.setSex(index)
            }
        }

        binding.btnNext.setOnClickListener {
            //TODO: 다음 화면으로 이동
            //
        }

    }
}