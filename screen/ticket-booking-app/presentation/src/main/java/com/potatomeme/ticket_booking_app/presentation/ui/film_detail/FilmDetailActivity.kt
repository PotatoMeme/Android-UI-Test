package com.potatomeme.ticket_booking_app.presentation.ui.film_detail

import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.presentation.R
import com.potatomeme.ticket_booking_app.presentation.databinding.ActivityFilmDetailBinding
import com.potatomeme.ticket_booking_app.presentation.model.ParcelableFilm

class FilmDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmDetailBinding
    private var film: ParcelableFilm? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFilmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        film = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("film", ParcelableFilm::class.java)
        } else {
            intent.getParcelableExtra("film")
        }

        if (film == null) finish()
    }
}