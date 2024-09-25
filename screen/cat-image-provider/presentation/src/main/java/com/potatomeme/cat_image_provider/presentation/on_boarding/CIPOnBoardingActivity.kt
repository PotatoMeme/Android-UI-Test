package com.potatomeme.cat_image_provider.presentation.on_boarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.potatomeme.cat_image_provider.presentation.databinding.ActivityCipOnBoardingBinding
import com.potatomeme.cat_image_provider.presentation.home.CIPHomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CIPOnBoardingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCipOnBoardingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCipOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            delay(3000)
            startActivity(Intent(this@CIPOnBoardingActivity, CIPHomeActivity::class.java))
            finish()
        }
    }
}