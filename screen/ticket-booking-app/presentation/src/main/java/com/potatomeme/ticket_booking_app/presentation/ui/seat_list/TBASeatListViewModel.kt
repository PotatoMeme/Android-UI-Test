package com.potatomeme.ticket_booking_app.presentation.ui.seat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.ticket_booking_app.domain.entity.SeatEntity
import com.potatomeme.ticket_booking_app.domain.usecase.RequestDateSlotsUseCase
import com.potatomeme.ticket_booking_app.domain.usecase.RequestSeatsUseCase
import com.potatomeme.ticket_booking_app.domain.usecase.RequestTimeSlotsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TBASeatListViewModel @Inject constructor(
    private val requestSeatsUseCase: RequestSeatsUseCase,
    private val requestTimeSlotsUseCase: RequestTimeSlotsUseCase,
    private val requestDateSlotsUseCase: RequestDateSlotsUseCase,
) : ViewModel() {
    // 좌석
    private val _seatFlow = MutableStateFlow<VmLoad<SeatEntity>>(VmLoad.Loading)
    val seatFlow: StateFlow<VmLoad<SeatEntity>> = _seatFlow.asStateFlow()

    // 날짜
    private val _dateFlow = MutableStateFlow<VmLoad<String>>(VmLoad.Loading)
    val dateFlow: StateFlow<VmLoad<String>> = _dateFlow.asStateFlow()

    private val _selectedDatePositionFlow = MutableStateFlow(Pair(0, 0))
    val selectedDatePositionFlow: StateFlow<Pair<Int, Int>> =
        _selectedDatePositionFlow.asStateFlow()

    // 시간
    private val _timeFlow = MutableStateFlow<VmLoad<String>>(VmLoad.Loading)
    val timeFlow: StateFlow<VmLoad<String>> = _timeFlow.asStateFlow()

    private val _selectedTimePositionFlow = MutableStateFlow(Pair(0, 0))
    val selectedTimePositionFlow: StateFlow<Pair<Int, Int>> =
        _selectedTimePositionFlow.asStateFlow()


    //request
    fun requestSeat(id: String) = viewModelScope.launch {
        if (seatFlow.value is VmLoad.Loaded) _seatFlow.value = VmLoad.Loading
        val seatList = requestSeatsUseCase.invoke(id)
        _seatFlow.value = VmLoad.Loaded.Requested(seatList)
    }

    fun requestDate(id: String) = viewModelScope.launch {
        if (dateFlow.value is VmLoad.Loaded) _dateFlow.value = VmLoad.Loading
        val dateList = requestDateSlotsUseCase.invoke(id)
        _dateFlow.value = VmLoad.Loaded.Requested(dateList)
        _selectedDatePositionFlow.value = Pair(-1, 0)
    }

    fun requestTime(id: String) = viewModelScope.launch {
        if (timeFlow.value is VmLoad.Loaded) _timeFlow.value = VmLoad.Loading
        val selectedDate = (dateFlow.value as? VmLoad.Loaded<String>)
            ?.list?.getOrNull(selectedDatePositionFlow.value.second) ?: return@launch
        val timeList = requestTimeSlotsUseCase.invoke(id, selectedDate)
        _timeFlow.value = VmLoad.Loaded.Requested(timeList)
        _selectedTimePositionFlow.value = Pair(-1, 0)
    }

    //update
    fun updateSeat(position: Int, seatEntity: SeatEntity) = viewModelScope.launch {
        val seatLoad = seatFlow.value
        if (seatLoad is VmLoad.Loaded) {
            seatLoad.list.toMutableList().let {
                it[position] = seatEntity
                _seatFlow.value = VmLoad.Loaded.Updated(position, it)
            }
        }
    }

    fun updateSelectedDate(position: Int) = viewModelScope.launch {
        _selectedDatePositionFlow.value = Pair(selectedDatePositionFlow.value.second, position)
        initSelectedTimeAndSeat()
    }

    fun updateSelectedTime(position: Int) = viewModelScope.launch {
        _selectedTimePositionFlow.value = Pair(selectedTimePositionFlow.value.second, position)
        val seatList = requestSeatsUseCase.invoke("")
        _seatFlow.value = VmLoad.Loaded.Requested(seatList)
    }

    private fun initSelectedTimeAndSeat() = viewModelScope.launch {
        val seatList = requestSeatsUseCase.invoke("")
        _seatFlow.value = VmLoad.Loaded.Requested(seatList)
        _selectedTimePositionFlow.value = Pair(selectedTimePositionFlow.value.second, 0)
    }
}

sealed class VmLoad<out T> {
    sealed class Loaded<T>(val list: List<T>) : VmLoad<T>() {
        class Requested<T>(list: List<T>) : Loaded<T>(list)
        class Updated<T>(val pos: Int, list: List<T>) : Loaded<T>(list)
    }

    data object Loading : VmLoad<Nothing>()
}