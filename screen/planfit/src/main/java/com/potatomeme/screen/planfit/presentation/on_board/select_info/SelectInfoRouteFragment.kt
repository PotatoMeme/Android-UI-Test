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
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoRouteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectInfoRouteFragment : Fragment() {
    private lateinit var binding: FragmentSelectinfoRouteBinding
    private val viewModel: SelectInfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSelectinfoRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toggleList = listOf(
            binding.toggleInstagramFacebook,
            binding.toggleEtc,
            binding.toggleYoutube,
            binding.toggleFriend,
            binding.toggleBlog,
        )

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.infoRoute.collect {
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
                viewModel.setRoute(index)
            }
        }

        binding.btnNext.setOnClickListener {
            //TODO: 다음 화면으로 이동
            viewModel.setState(SelectInfoState.STATE_COMPLETE)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setState(SelectInfoState.STATE_ROUTE)
    }
}