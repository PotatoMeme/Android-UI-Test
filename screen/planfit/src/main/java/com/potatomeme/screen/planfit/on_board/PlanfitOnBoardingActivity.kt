package com.potatomeme.screen.planfit.on_board

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.potatomeme.screen.planfit.databinding.ActivityPlanfitOnBoardingBinding

class PlanfitOnBoardingActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityPlanfitOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanfitOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}