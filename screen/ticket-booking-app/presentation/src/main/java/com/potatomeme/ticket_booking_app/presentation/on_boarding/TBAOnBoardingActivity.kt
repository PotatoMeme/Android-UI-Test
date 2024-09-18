package com.potatomeme.ticket_booking_app.presentation.on_boarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.potatomeme.ticket_booking_app.domain.usecase.RequestFilmsUseCase
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ActivityTbaOnBoadingBinding
import com.potatomeme.ticket_booking_app.presentation.on_boarding.main.TBAMainActivity
import kotlinx.coroutines.launch
import javax.inject.Inject

class TBAOnBoardingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTbaOnBoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTbaOnBoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener {
            //todo 다음 액티비티로 이동
            startActivity(Intent(this, TBAMainActivity::class.java))
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}