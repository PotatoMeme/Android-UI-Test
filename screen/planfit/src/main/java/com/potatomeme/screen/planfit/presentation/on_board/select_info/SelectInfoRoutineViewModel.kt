package com.potatomeme.screen.planfit.presentation.on_board.select_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.screen.planfit.data.model.Gym
import com.potatomeme.screen.planfit.data.model.UserInfo
import com.potatomeme.screen.planfit.domain.usecase.PostPlanfitUserInfoUseCase
import com.potatomeme.screen.planfit.domain.usecase.RequestGymListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectInfoRoutineViewModel @Inject constructor(
) : ViewModel() {
    private val _infoRoutine: MutableStateFlow<SelectInfo> =
        MutableStateFlow(SelectInfo.None)
    val infoRoutine: StateFlow<SelectInfo> = _infoRoutine

    //set
    fun setRoutine(level: Int) = viewModelScope.launch {
        _infoRoutine.value = SelectInfo.SelectInfoSelected(level)
    }

    //post
    fun postRoutine() = viewModelScope.launch{
        //todo: post 루틴
    }
}