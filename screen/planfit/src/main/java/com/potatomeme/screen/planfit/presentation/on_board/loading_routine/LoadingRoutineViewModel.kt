package com.potatomeme.screen.planfit.presentation.on_board.loading_routine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingRoutineViewModel @Inject constructor() : ViewModel() {
    private val _firstStepCorrect = MutableStateFlow(false)
    val firstStepCorrect : StateFlow<Boolean> = _firstStepCorrect

    private val _secondStepCorrect = MutableStateFlow(false)
    val secondStepCorrect : StateFlow<Boolean> = _secondStepCorrect

    private val _thirdStepCorrect = MutableStateFlow(false)
    val thirdStepCorrect : StateFlow<Boolean> = _thirdStepCorrect

    private val _fourthStepCorrect = MutableStateFlow(false)
    val fourthStepCorrect : StateFlow<Boolean> = _fourthStepCorrect

    fun startLoading() = viewModelScope.launch {
        delay(1000L)
        _firstStepCorrect.value = true
        delay(1000L)
        _secondStepCorrect.value = true
        delay(1000L)
        _thirdStepCorrect.value = true
        delay(1000L)
        _fourthStepCorrect.value = true
    }
}