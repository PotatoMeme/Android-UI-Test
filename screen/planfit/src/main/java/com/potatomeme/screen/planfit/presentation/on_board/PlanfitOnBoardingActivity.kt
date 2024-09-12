package com.potatomeme.screen.planfit.presentation.on_board

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.potatomeme.screen.planfit.databinding.ActivityPlanfitOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanfitOnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlanfitOnBoardingBinding
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "알림 권한이 허용되었습니다.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "알림 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanfitOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun requestNotificationPermission(onGranted: () -> Unit) {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            onGranted()
        } else {
            activityResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

}