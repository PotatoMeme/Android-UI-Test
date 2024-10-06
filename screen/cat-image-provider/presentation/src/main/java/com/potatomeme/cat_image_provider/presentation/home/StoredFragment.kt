package com.potatomeme.cat_image_provider.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.potatomeme.cat_image_provider.presentation.R
import com.potatomeme.cat_image_provider.presentation.databinding.FragmentHomeBinding
import com.potatomeme.cat_image_provider.presentation.databinding.FragmentStoredBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoredFragment : Fragment() {
    private var binding: FragmentStoredBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    private val adapter: CatPagingAdapter by lazy {
        CatPagingAdapter {
            //todo item click
            viewModel.deleteCat(it.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStoredBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.run {
            rvRequestedCatImages.apply {
                adapter = this@StoredFragment.adapter
                layoutManager = GridLayoutManager(this@StoredFragment.context, 2)
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.getSavedCats.collect {
                        adapter.submitData(it)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}