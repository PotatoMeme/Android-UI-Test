package com.potatomeme.ticket_booking_app.presentation.on_boarding.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.ticket_booking_app.domain.entity.FilmEntity
import com.potatomeme.ticket_booking_app.domain.usecase.RequestFilmsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TBAMainViewModel @Inject constructor(
    private val requestFilmsUseCase: RequestFilmsUseCase
) : ViewModel() {

    private val _films: MutableStateFlow<List<FilmEntity>> = MutableStateFlow(emptyList())
    val films: StateFlow<List<FilmEntity>> = _films


    fun requestFilms() = viewModelScope.launch() {
        val films = requestFilmsUseCase.invoke()
        _films.value = films
    }

}