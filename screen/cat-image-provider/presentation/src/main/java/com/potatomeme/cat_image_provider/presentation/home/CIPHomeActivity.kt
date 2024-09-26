package com.potatomeme.cat_image_provider.presentation.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.potatomeme.cat_image_provider.presentation.R
import com.potatomeme.cat_image_provider.presentation.databinding.ActivityCipHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CIPHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCipHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCipHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}