package com.potatomeme.ticket_booking_app.presentation.ui.seat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.ticket_booking_app.domain.entity.SeatEntity
import com.potatomeme.ticket_booking_app.domain.usecase.RequestSeatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TBASeatListViewModel @Inject constructor(
    private val requestSeatsUseCase: RequestSeatsUseCase
) : ViewModel() {
    private val _seatFlow = MutableStateFlow<SeatLoad>(SeatLoad.Loading)
    val seatFlow: StateFlow<SeatLoad> = _seatFlow.asStateFlow()

    fun requestSeat(id: String) = viewModelScope.launch{
        if (seatFlow.value is SeatLoad.Loaded) _seatFlow.value = SeatLoad.Loading
        delay(1000)
        val seatList = requestSeatsUseCase.invoke(id)
        _seatFlow.value = SeatLoad.Loaded.Requested(seatList)
    }

    fun updateSeat(position: Int, seatEntity: SeatEntity) {
        val seatLoad = seatFlow.value
        if (seatLoad is SeatLoad.Loaded) {
            seatLoad.list.toMutableList().let {
                it[position] = seatEntity
                _seatFlow.value = SeatLoad.Loaded.Updated(position,it)
            }
        }
    }
}

sealed class SeatLoad {
    sealed class Loaded(val list: List<SeatEntity>) : SeatLoad(){
        class Requested(list: List<SeatEntity>) : Loaded(list)
        class Updated(val pos : Int,list: List<SeatEntity>) : Loaded(list)
    }
    data object Loading : SeatLoad()
}