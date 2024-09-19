package com.potatomeme.ticket_booking_app.presentation.on_boarding.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.potatomeme.ticket_booking_app.domain.entity.BannerEntity
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ActivityTbaMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

@AndroidEntryPoint
class TBAMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTbaMainBinding
    private val viewModel: TBAMainViewModel by viewModels()
    private val bannerAdapter = BannerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTbaMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.requestFilms()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    //확인용
                    viewModel.films.collect {
                        /* Log.d(TAG, "onCreate: ${it.size}")
                         it.forEach {
                             Log.d(TAG, "onCreate: ${it.casts.size}")
                         }*/
                    }
                }
            }
        }
        initBanner()
    }

    private fun initBanner() {
        viewModel.requestBanners()

        binding.vpBanner.apply {
            adapter = bannerAdapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(
                CompositePageTransformer().apply {
                    addTransformer(MarginPageTransformer(40))
                    addTransformer { page, position ->
                        val r = 1 - abs(position)
                        page.scaleY = 0.85f + r * 0.15f
                    }
                }
            )

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    handlePageChange(position)
                }
            })
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                //viewmodel flow collect
                launch {
                    viewModel.banners.collect {
                        if (it.isEmpty()) {
                            binding.progressBarSlider.visibility = View.VISIBLE
                        } else {
                            binding.progressBarSlider.visibility = View.GONE
                            val circularList = makeCircularList(it)
                            bannerAdapter.submitList(circularList)
                            binding.vpBanner.setCurrentItem(1, false)
                        }
                    }
                }
                //viewpager auto slide banner
                launch {
                    while (true) {
                        delay(AUTO_SLIDE_DURATION)
                        val itemCount = binding.vpBanner.adapter?.itemCount ?: 0
                        if (itemCount > 0) {
                            val nextItem = (binding.vpBanner.currentItem + 1) % itemCount
                            binding.vpBanner.setCurrentItem(nextItem, true)
                        }
                    }
                }
            }
        }
    }

    private fun makeCircularList(banners: List<BannerEntity>): List<BannerEntity> {
        val circularList = mutableListOf<BannerEntity>()
        if (banners.isNotEmpty()) {
            circularList.add(banners.last())
            circularList.addAll(banners)
            circularList.add(banners.first())
        }
        return circularList
    }

    private fun handlePageChange(position: Int) {
        val itemCount = bannerAdapter.itemCount
        if (position == 0) {
            binding.vpBanner.setCurrentItem(itemCount - 2, false)
        }
        else if (position == itemCount - 1) {
            binding.vpBanner.setCurrentItem(1, false)
        }
    }

    companion object {
        private const val TAG = "TBAMainActivity"
        private const val AUTO_SLIDE_DURATION = 3000L
    }
}