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

    //운동 level
    private val _infoExerciseLevel: MutableStateFlow<ExerciseLevel> =
        MutableStateFlow(ExerciseLevel.None)
    val infoExerciseLevel: StateFlow<ExerciseLevel> = _infoExerciseLevel

    fun setExerciseLevel(level: Int) = viewModelScope.launch {
        _infoExerciseLevel.value = ExerciseLevel.ExerciseLevelSelected(level)
    }
}

sealed class ExerciseLevel {
    data object None : ExerciseLevel()
    data class ExerciseLevelSelected(val level: Int) : ExerciseLevel()
    companion object {
        const val INTRODUCTION = 0
        const val BEGINNER = 1
        const val MIDDLE = 2
        const val HIGH = 3
        const val MASTER = 4
    }
}