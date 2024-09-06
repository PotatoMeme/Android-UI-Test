package com.potatomeme.screen.planfit.presentation.on_board

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.potatomeme.screen.planfit.databinding.ActivityPlanfitOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanfitOnBoardingActivity : AppCompatActivity() {
    private  lateinit var binding : ActivityPlanfitOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanfitOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}