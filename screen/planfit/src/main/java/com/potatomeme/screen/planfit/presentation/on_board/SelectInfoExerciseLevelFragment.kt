package com.potatomeme.screen.planfit.presentation.on_board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoExerciseLevelBinding

class SelectInfoExerciseLevelFragment : Fragment(){
    private lateinit var binding: FragmentSelectinfoExerciseLevelBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectinfoExerciseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}