package com.potatomeme.ticket_booking_app.presentation.on_boarding.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.ticket_booking_app.domain.entity.BannerEntity
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.domain.usecase.RequestBannersUseCase
import com.potatomeme.ticket_booking_app.domain.usecase.RequestTopFilmsUseCase
import com.potatomeme.ticket_booking_app.domain.usecase.RequestUpcomingFilmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TBAMainViewModel @Inject constructor(
    private val requestTopFilmsUseCase: RequestTopFilmsUseCase,
    private val requestUpcomingFilmsUseCase: RequestUpcomingFilmsUseCase,
    private val requestBannersUseCase: RequestBannersUseCase,
) : ViewModel() {

    private val _topFilms: MutableStateFlow<List<FilmEntity>> = MutableStateFlow(emptyList())
    val topFilms: StateFlow<List<FilmEntity>> = _topFilms

    private val _upcomingFilms: MutableStateFlow<List<FilmEntity>> = MutableStateFlow(emptyList())
    val upcomingFilms: StateFlow<List<FilmEntity>> = _upcomingFilms


    private val _banners: MutableStateFlow<List<BannerEntity>> = MutableStateFlow(emptyList())
    val banners: StateFlow<List<BannerEntity>> = _banners

    // request
    fun requestTopFilms() = viewModelScope.launch() {
        val films = requestTopFilmsUseCase.invoke()
        _topFilms.value = films
    }

    fun requestUpcomingFilms() = viewModelScope.launch() {
        val films = requestUpcomingFilmsUseCase.invoke()
        _upcomingFilms.value = films
    }

    fun requestBanners() = viewModelScope.launch() {
        val banners = requestBannersUseCase.invoke()
        _banners.value = banners
    }

}