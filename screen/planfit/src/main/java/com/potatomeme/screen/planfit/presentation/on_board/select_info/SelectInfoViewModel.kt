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
class SelectInfoViewModel @Inject constructor(
    private val requestGymListUseCase: RequestGymListUseCase,
    private val postPlanfitUserInfoUseCase: PostPlanfitUserInfoUseCase
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

    //운동회수 : 4
    private val _infoExerciseTimes: MutableStateFlow<SelectInfo> = MutableStateFlow(SelectInfo.None)
    val infoExerciseTimes: StateFlow<SelectInfo> = _infoExerciseTimes

    //신체 목표 : 5
    private val _infoBodyGoal: MutableStateFlow<List<Boolean>> =
        MutableStateFlow(List(12) { false })
    val infoBodyGoal: StateFlow<List<Boolean>> = _infoBodyGoal

    //신체 정보,성별 : 6
    private val _infoSex: MutableStateFlow<SelectInfo> = MutableStateFlow(SelectInfo.None)
    val infoSex: StateFlow<SelectInfo> = _infoSex

    //신체 정보,생년월일 : 7
    private val _infoBirth: MutableStateFlow<String> = MutableStateFlow("")
    val infoBirth: StateFlow<String> = _infoBirth

    //신체 정보,몸무게 : 7
    private val _infoWeight: MutableStateFlow<String> = MutableStateFlow("")
    val infoWeight: StateFlow<String> = _infoWeight

    //신체 정보,키 : 7
    private val _infoHeight: MutableStateFlow<String> = MutableStateFlow("")
    val infoHeight: StateFlow<String> = _infoHeight

    //신체 정보, 경로 : 8
    private val _infoRoute: MutableStateFlow<SelectInfo> = MutableStateFlow(SelectInfo.None)
    val infoRoute: StateFlow<SelectInfo> = _infoRoute


    //setter
    fun setState(state: Int) = viewModelScope.launch {
        _state.value = state
    }

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

    fun setBodyInfo(date: String, weight: String, height: String) = viewModelScope.launch {
        _infoBirth.value = date
        _infoWeight.value = weight
        _infoHeight.value = height
    }

    fun setRoute(route: Int) = viewModelScope.launch {
        _infoRoute.value = SelectInfo.SelectInfoSelected(route)
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

    //post
    fun postUserInfo() = viewModelScope.launch {
        val level = (infoExerciseLevel.value as SelectInfo.SelectInfoSelected).level
        val place = (infoPlace.value as SelectInfo.SelectInfoSelected).level

        val gym = selectedGym.value
        val equipment = (infoEquipmentType.value as? SelectInfo.SelectInfoSelected)?.level

        val times = (infoExerciseTimes.value as SelectInfo.SelectInfoSelected).level
        val bodyGoal = infoBodyGoal.value
            .mapIndexed { index, b -> Pair(index, b) }
            .filter { it.second }
            .map { it.first }
        val sex = infoSex.value
        val birth = infoBirth.value
        val weight = infoWeight.value
        val height = infoHeight.value
        val route = infoRoute.value

         val userInfo = UserInfo(
            level = level,
            place = place,
            gym = gym,
            equipmentType = equipment,
            times = times,
            bodyGoal = bodyGoal,
            sex = sex,
            birth = birth,
            weight = weight,
            height = height,
            route = route
        )

        postPlanfitUserInfoUseCase(userInfo)
    }
}

sealed class SelectInfo {
    data object None : SelectInfo()
    data class SelectInfoSelected(val level: Int) : SelectInfo()
}