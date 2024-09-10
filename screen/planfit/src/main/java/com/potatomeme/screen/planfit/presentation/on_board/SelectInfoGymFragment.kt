package com.potatomeme.screen.planfit.presentation.on_board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.potatomeme.screen.planfit.R
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoExerciseLevelBinding
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoGymBinding
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoPlaceBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectInfoGymFragment : Fragment() {
    private lateinit var binding: FragmentSelectinfoGymBinding
    private val viewModel: SelectInfoViewModel by activityViewModels()
    private val adapter: GymListAdapter = GymListAdapter { gym ->
        val modal = GymCheckBottomSheet(gym) { gym ->
            viewModel.setSelectedGym(gym)
            //findNavController().navigate(R.id.action_selectInfoGymFragment_to_selectInfoPlaceFragment)
        }
        modal.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
        modal.show(childFragmentManager, GymCheckBottomSheet.TAG)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSelectinfoGymBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.requestGymList("")

        binding.rvGym.apply {
            adapter = this@SelectInfoGymFragment.adapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.infoGymList.collect {
                    adapter.submitList(it)
                }
            }
        }

    }
}