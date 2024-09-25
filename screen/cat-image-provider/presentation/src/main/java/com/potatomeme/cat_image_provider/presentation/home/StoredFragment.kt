package com.potatomeme.cat_image_provider.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.potatomeme.cat_image_provider.presentation.R
import com.potatomeme.cat_image_provider.presentation.databinding.FragmentHomeBinding
import com.potatomeme.cat_image_provider.presentation.databinding.FragmentStoredBinding


class StoredFragment : Fragment() {
    private var binding: FragmentStoredBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStoredBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}