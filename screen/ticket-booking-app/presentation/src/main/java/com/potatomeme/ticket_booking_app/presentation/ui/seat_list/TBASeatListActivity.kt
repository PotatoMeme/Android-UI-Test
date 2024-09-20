package com.potatomeme.ticket_booking_app.presentation.ui.seat_list

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ActivityTbaSeatListBinding
import com.potatomeme.ticket_booking_app.presentation.model.ParcelableFilm
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TBASeatListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTbaSeatListBinding
    private val film: ParcelableFilm by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("film", ParcelableFilm::class.java)!!
        } else {
            intent.getParcelableExtra("film")!!
        }
    }
    private val viewModel: TBASeatListViewModel by viewModels()
    private val seatAdapter: SeatListAdapter by lazy {
        SeatListAdapter(
            listOf(
                getColor(R.color.white),
                getColor(R.color.black2),
                getColor(R.color.black2),
            )
        ) { position, seatEntity ->
            viewModel.updateSeat(position, seatEntity )
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTbaSeatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        binding.seatRecyclerview.apply {
            val gridLayoutManager = GridLayoutManager(this@TBASeatListActivity, 7)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % 7 == 3) 1 else 1
                }
            }
            layoutManager = gridLayoutManager
            adapter = seatAdapter
            isNestedScrollingEnabled = false
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.seatFlow.collect {
                        when (it) {
                            is SeatLoad.Loaded.Requested -> {
                                binding.seatRecyclerview.visibility = android.view.View.VISIBLE
                                binding.seatProgressBar.visibility = android.view.View.GONE
                                seatAdapter.submitList(it.list)
                            }

                            is SeatLoad.Loaded.Updated -> {
                                binding.seatRecyclerview.visibility = android.view.View.VISIBLE
                                binding.seatProgressBar.visibility = android.view.View.GONE
                                seatAdapter.updatePosition(it.pos, it.list)
                            }

                            SeatLoad.Loading -> {
                                binding.seatRecyclerview.visibility = android.view.View.INVISIBLE
                                binding.seatProgressBar.visibility = android.view.View.VISIBLE
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestSeat(film.title!!)
    }
}

