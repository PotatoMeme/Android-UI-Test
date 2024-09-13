package com.potatomeme.screen.planfit.presentation.on_board.select_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.screen.planfit.data.model.Routine
import com.potatomeme.screen.planfit.domain.usecase.PostPlanfitRoutineUseCase
import com.potatomeme.screen.planfit.domain.usecase.RequestPlanfitRoutineListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectInfoRoutineViewModel @Inject constructor(
    private val requestPlanfitRoutineListUseCase: RequestPlanfitRoutineListUseCase,
    private val postPlanfitRoutineUseCase: PostPlanfitRoutineUseCase
) : ViewModel() {
    private val _infoRoutine: MutableStateFlow<SelectInfo> =
        MutableStateFlow(SelectInfo.None)
    val infoRoutine: StateFlow<SelectInfo> = _infoRoutine

    private val _routineList: MutableStateFlow<List<Routine>> = MutableStateFlow(listOf())
    val routineList: StateFlow<List<Routine>> = _routineList

    init {
        requestRoutineList()
    }

    //set
    fun setRoutine(level: Int) = viewModelScope.launch {
        _infoRoutine.value = SelectInfo.SelectInfoSelected(level)
    }

    //request
    private fun requestRoutineList() = viewModelScope.launch {
        _routineList.value = requestPlanfitRoutineListUseCase()
    }

    //post
    fun postRoutine() = viewModelScope.launch{
        //todo: post 루틴
        val routineIdx = (infoRoutine.value as SelectInfo.SelectInfoSelected).level
        val selectedRoutine = routineList.value[routineIdx]
        postPlanfitRoutineUseCase(selectedRoutine)
    }
}