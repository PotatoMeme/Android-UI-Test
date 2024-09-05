package com.potatomeme.custom_ui.chart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.potatomeme.custom_ui.chart.databinding.ActivityCustomViewChartBinding

class CustomViewChartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCustomViewChartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomViewChartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = mutableListOf(
            20f,70f,150f,10f,60f,90f
        )
        binding.chart.setData(data)
    }
}