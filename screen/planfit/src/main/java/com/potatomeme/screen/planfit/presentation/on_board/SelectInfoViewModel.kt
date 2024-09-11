package com.potatomeme.screen.planfit.presentation.on_board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.screen.planfit.data.model.Gym
import com.potatomeme.screen.planfit.domain.usecase.RequestGymListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectInfoViewModel @Inject constructor(
    private val requestGymListUseCase: RequestGymListUseCase,
) : ViewModel() {
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

    //핼스장 정보 : 3 - A
    private val _infoGymList: MutableStateFlow<List<Gym>> = MutableStateFlow(listOf())
    val infoGymList: StateFlow<List<Gym>> = _infoGymList

    private val _selectedGym: MutableStateFlow<Gym?> = MutableStateFlow(null)
    val selectedGym: StateFlow<Gym?> = _selectedGym

    //장비 타입 : 3 - B
    private val _infoEquipmentType: MutableStateFlow<SelectInfo> = MutableStateFlow(SelectInfo.None)
    val infoEquipmentType: StateFlow<SelectInfo> = _infoEquipmentType

    //운동회수 : 5
    private val _infoExerciseTimes: MutableStateFlow<SelectInfo> = MutableStateFlow(SelectInfo.None)
    val infoExerciseTimes: StateFlow<SelectInfo> = _infoExerciseTimes

    //신체 목표 : 6
    private val _infoBodyGoal: MutableStateFlow<List<Boolean>> =
        MutableStateFlow(List(12) { false })
    val infoBodyGoal: StateFlow<List<Boolean>> = _infoBodyGoal

    //신체 정보,성별 : 7
    private val _infoSex: MutableStateFlow<SelectInfo> = MutableStateFlow(SelectInfo.None)
    val infoSex: StateFlow<SelectInfo> = _infoSex


    //setter
    fun setExerciseLevel(level: Int) = viewModelScope.launch {
        _infoExerciseLevel.value = SelectInfo.SelectInfoSelected(level)
    }

    fun setPlace(level: Int) = viewModelScope.launch {
        _infoPlace.value = SelectInfo.SelectInfoSelected(level)
    }

    fun setEquipmentType(level: Int) = viewModelScope.launch {
        _infoEquipmentType.value = SelectInfo.SelectInfoSelected(level)
    }

    fun setSelectedGym(gym: Gym) = viewModelScope.launch {
        _selectedGym.value = gym
    }

    fun setExerciseTimes(times: Int) = viewModelScope.launch {
        _infoExerciseTimes.value = SelectInfo.SelectInfoSelected(times)
    }

    fun setSex(sex: Int) = viewModelScope.launch {
        _infoSex.value = SelectInfo.SelectInfoSelected(sex)
    }

    //update
    fun updateSelectedBodyGoalListWithIndex(index: Int) = viewModelScope.launch {
        val newList = _infoBodyGoal.value.toMutableList()
        newList[index] = !newList[index]
        _infoBodyGoal.value = newList
    }

    //request
    fun requestGymList(keyword: String) = viewModelScope.launch {
        _infoGymList.value = requestGymListUseCase(keyword)
    }
}

sealed class SelectInfo {
    data object None : SelectInfo()
    data class SelectInfoSelected(val level: Int) : SelectInfo()
}