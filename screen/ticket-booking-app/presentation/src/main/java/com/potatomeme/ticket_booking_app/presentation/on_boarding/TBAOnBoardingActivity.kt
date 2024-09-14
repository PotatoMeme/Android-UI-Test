package com.potatomeme.ticket_booking_app.presentation.on_boarding

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ActivityTbaOnBoadingBinding

class TBAOnBoardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityTbaOnBoadingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTbaOnBoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStart.setOnClickListener {
            //todo 다음 액티비티로 이동
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}