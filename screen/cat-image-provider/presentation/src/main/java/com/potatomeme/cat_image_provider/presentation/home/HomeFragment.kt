package com.potatomeme.cat_image_provider.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.potatomeme.cat_image_provider.presentation.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by activityViewModels()
    /*private val adapter: CatListAdapter by lazy {
        CatListAdapter {
            //todo item click
        }
    }*/
    private val adapter: CatPagingAdapter by lazy {
        CatPagingAdapter {
            //todo item click
            viewModel.insertCat(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.requestCats()
        //viewModel.requestPagingCats()
        binding?.run {
            rvRequestedCatImages.apply {
                adapter = this@HomeFragment.adapter
                layoutManager =
                    StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
                        gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
                    }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                /*launch {
                    viewModel.requestCats.collect {
                        adapter.submitList(it)
                    }
                }*/
                launch {
                    viewModel.requestPagingCats.collect {
                        Log.d("TAG", "requestPagingCats changed $it")
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