package com.potatomeme.screen.planfit.presentation.on_board

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.screen.planfit.data.model.PlanfitLoginType
import com.potatomeme.screen.planfit.domain.usecase.GetPlanfitLoginType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanfitSplashViewModel @Inject constructor(
    private val getPlanfitLoginType: GetPlanfitLoginType,
) : ViewModel() {
    private val _isLoginFlow = MutableStateFlow<LoginEvent>(LoginEvent.UnChecked)
    val isLoginFlow: StateFlow<LoginEvent>
        get() = _isLoginFlow

    fun checkLoginState() = viewModelScope.launch {
        val loginType = getPlanfitLoginType()
        delay(3000L)
        _isLoginFlow.emit(LoginEvent.Login(loginType))
        if (loginType == PlanfitLoginType.None)
            _isLoginFlow.emit(LoginEvent.UnChecked)
    }
}

sealed class LoginEvent {
    data object UnChecked : LoginEvent()
    data class Login(
        val loginType: PlanfitLoginType,
    ) : LoginEvent()
}


