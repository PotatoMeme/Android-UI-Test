package com.potatomeme.screen.planfit.presentation.on_board

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.potatomeme.screen.planfit.databinding.FragmentSelectinfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectInfoFragment : Fragment() {
    private lateinit var binding: FragmentSelectinfoBinding

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
    }
}