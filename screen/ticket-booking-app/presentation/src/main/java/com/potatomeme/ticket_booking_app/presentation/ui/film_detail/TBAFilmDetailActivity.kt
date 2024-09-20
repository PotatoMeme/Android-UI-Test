package com.potatomeme.ticket_booking_app.presentation.ui.film_detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.potatomeme.ticket_booking_app.presentation.databinding.ActivityTbaFilmDetailBinding
import com.potatomeme.ticket_booking_app.presentation.mapper.DomainEntityParcelableMapper
import com.potatomeme.ticket_booking_app.presentation.model.ParcelableFilm
import com.potatomeme.ticket_booking_app.presentation.ui.seat_list.TBASeatListActivity
import eightbitlab.com.blurview.RenderEffectBlur
import eightbitlab.com.blurview.RenderScriptBlur

class TBAFilmDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTbaFilmDetailBinding

    private val film: ParcelableFilm by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("film", ParcelableFilm::class.java)!!
        } else {
            intent.getParcelableExtra("film")!!
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTbaFilmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        initViews()
    }

    private fun initViews() {
        val requestOptions =
            RequestOptions().transform(CenterCrop(), GranularRoundedCorners(0f, 0f, 50f, 50f))

        Glide.with(this)
            .load(film.poster)
            .apply(requestOptions)
            .into(binding.filmPic)

        binding.titleTxt.text = film.title
        binding.imdbTxt.text = "IMDB ${film.imdb}"
        binding.movieTimeTxt.text = "${film.year} - ${film.time}"
        binding.movieSummeryTxt.text = film.description

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.buyTicketBtn.setOnClickListener {
            //todo move next Activity with film
            val intent = Intent(this, TBASeatListActivity::class.java).apply {
                putExtra("film", film)
            }
            startActivity(intent)
        }

        val radius = 10f
        val decorView = window.decorView
        val rootView = decorView.findViewById<ViewGroup>(android.R.id.content)
        val windowsBackground = decorView.background

        // RenderScriptBlur 가 deprecated 되고 대안책으로 RenderEffectBlur를 사용하라고 합니다.
        // RenderEffectBlur를 sdk 버전이 31버전 이후를 원함
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.blurView.setupWith(rootView, RenderEffectBlur())
                .setFrameClearDrawable(windowsBackground)
                .setBlurRadius(radius)
        } else {
            binding.blurView.setupWith(rootView, RenderScriptBlur(this))
                .setFrameClearDrawable(windowsBackground)
                .setBlurRadius(radius)
        }

        binding.blurView.outlineProvider = ViewOutlineProvider.BACKGROUND
        binding.blurView.clipToOutline = true

        binding.genreView.adapter = CategoryEachFilmAdapter(film.genre)
        binding.genreView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        binding.castListView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.castListView.adapter = CastListAdapter(film.casts)
    }
}