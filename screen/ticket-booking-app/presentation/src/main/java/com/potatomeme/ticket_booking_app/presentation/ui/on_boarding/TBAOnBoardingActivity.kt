package com.potatomeme.ticket_booking_app.presentation.ui.on_boarding

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.potatomeme.ticket_booking_app.presentation.databinding.ActivityTbaOnBoadingBinding
import com.potatomeme.ticket_booking_app.presentation.ui.main.TBAMainActivity

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