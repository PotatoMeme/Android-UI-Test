package com.potatomeme.ticket_booking_app.presentation.ui.seat_list

import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.potatomeme.ticket_booking_app.domain.entity.SeatEntity
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
            viewModel.updateSeat(position, seatEntity)
        }
    }
    private val dateAdapter: DateAdapter by lazy {
        DateAdapter { selectPosition ->
            viewModel.updateSelectedDate(selectPosition)
        }
    }
    private val timeAdapter: TimeAdapter by lazy {
        TimeAdapter { selectPosition ->
            viewModel.updateSelectedTime(selectPosition)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTbaSeatListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initViews()
    }

    private fun initViews() {
        binding.backBtn.setOnClickListener {
            finish()
        }
        //seatRecyclerview init
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

        //dateRecyclerview init
        binding.dateRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(this@TBASeatListActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = dateAdapter
            isNestedScrollingEnabled = false
        }

        //timeRecyclerview init
        binding.timeRecyclerview.apply {
            layoutManager =
                LinearLayoutManager(this@TBASeatListActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = timeAdapter
            isNestedScrollingEnabled = false
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                //seatRecyclerview
                launch {
                    viewModel.seatFlow.collect {
                        when (it) {
                            is VmLoad.Loaded.Requested -> {
                                binding.seatRecyclerview.visibility = android.view.View.VISIBLE
                                binding.seatProgressBar.visibility = android.view.View.GONE
                                seatAdapter.submitList(it.list)
                                setPriceInfos(it.list)
                            }

                            is VmLoad.Loaded.Updated -> {
                                binding.seatRecyclerview.visibility = android.view.View.VISIBLE
                                binding.seatProgressBar.visibility = android.view.View.GONE
                                seatAdapter.updatePosition(it.pos, it.list)
                                setPriceInfos(it.list)
                            }

                            VmLoad.Loading -> {
                                binding.seatRecyclerview.visibility = android.view.View.INVISIBLE
                                binding.seatProgressBar.visibility = android.view.View.VISIBLE
                            }
                        }
                    }
                }
                //dateRecyclerview
                launch {
                    viewModel.dateFlow.collect {
                        when (it) {
                            is VmLoad.Loaded -> {
                                dateAdapter.submitList(it.list)
                            }

                            VmLoad.Loading -> {

                            }
                        }

                    }
                }
                //date selected
                launch {
                    viewModel.selectedDatePositionFlow.collect {
                        Log.d("TAG", "initViews: selectedDatePositionFlow updated")
                        dateAdapter.updateSelected(it.first, it.second)
                    }
                }
                //timeRecyclerview
                launch {
                    viewModel.timeFlow.collect {
                        when (it) {
                            is VmLoad.Loaded -> {
                                timeAdapter.submitList(it.list)
                            }

                            VmLoad.Loading -> {

                            }
                        }
                    }
                }
                //time selected
                launch {
                    viewModel.selectedTimePositionFlow.collect {
                        Log.d("TAG", "initViews: selectedTimePositionFlow updated")
                        timeAdapter.updateSelected(it.first, it.second)
                    }
                }
            }
        }
    }

    private fun setPriceInfos(list: List<SeatEntity>) {
        val selectedCount = list.count { it.status == SeatEntity.SeatStatus.SELECTED }
        binding.numberSelectedTxt.text = "$selectedCount Seat Selected"
        val df = DecimalFormat("#.##")
        val price = df.format(selectedCount * film.price).toDouble()
        binding.priceTxt.text = price.toString()

    }

    override fun onResume() {
        super.onResume()
        viewModel.requestSeat(film.title!!)
        viewModel.requestDate(film.title!!)
        viewModel.requestTime(film.title!!)
    }
}

