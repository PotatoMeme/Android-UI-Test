package com.potatomeme.screen.planfit.presentation.on_board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectInfoViewModel @Inject constructor() : ViewModel() {
    //select info state
    private val _state = MutableStateFlow(1)
    val state: StateFlow<Int> = _state

    //운동 level : 1
    private val _infoExerciseLevel: MutableStateFlow<SelectInfo> =
        MutableStateFlow(SelectInfo.None)
    val infoExerciseLevel: StateFlow<SelectInfo> = _infoExerciseLevel

    //운동 장소 : 2
    private val _infoPlace: MutableStateFlow<SelectInfo> =
        MutableStateFlow(SelectInfo.None)
    val infoPlace: StateFlow<SelectInfo> = _infoPlace

    fun setExerciseLevel(level: Int) = viewModelScope.launch {
        _infoExerciseLevel.value = SelectInfo.SelectInfoSelected(level)
    }

    fun setPlace(level: Int) = viewModelScope.launch {
        _infoPlace.value = SelectInfo.SelectInfoSelected(level)
    }
}

sealed class SelectInfo {
    data object None : SelectInfo()
    data class SelectInfoSelected(val level: Int) : SelectInfo()
}