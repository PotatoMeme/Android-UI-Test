package com.potatomeme.ticket_booking_app.presentation.on_boarding.main

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ActivityTbaMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TBAMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTbaMainBinding
    private val viewModel: TBAMainViewModel by viewModels()

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
                        Log.d(TAG, "onCreate: ${it.size}")
                        it.forEach {
                            Log.d(TAG, "onCreate: ${it.casts.size}")
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "TBAMainActivity"
    }
}