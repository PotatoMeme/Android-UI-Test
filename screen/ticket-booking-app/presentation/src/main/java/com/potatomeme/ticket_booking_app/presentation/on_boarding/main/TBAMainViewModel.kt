package com.potatomeme.ticket_booking_app.presentation.on_boarding.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.ticket_booking_app.domain.entity.BannerEntity
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.domain.usecase.RequestBannersUseCase
import com.potatomeme.ticket_booking_app.domain.usecase.RequestFilmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TBAMainViewModel @Inject constructor(
    private val requestFilmsUseCase: RequestFilmsUseCase,
    private val requestBannersUseCase: RequestBannersUseCase,
) : ViewModel() {

    private val _films: MutableStateFlow<List<FilmEntity>> = MutableStateFlow(emptyList())
    val films: StateFlow<List<FilmEntity>> = _films

    private val _banners: MutableStateFlow<List<BannerEntity>> = MutableStateFlow(emptyList())
    val banners: StateFlow<List<BannerEntity>> = _banners

    // request
    fun requestFilms() = viewModelScope.launch() {
        val films = requestFilmsUseCase.invoke()
        _films.value = films
    }

    fun requestBanners() = viewModelScope.launch() {
        val banners = requestBannersUseCase.invoke()
        _banners.value = banners
    }

}