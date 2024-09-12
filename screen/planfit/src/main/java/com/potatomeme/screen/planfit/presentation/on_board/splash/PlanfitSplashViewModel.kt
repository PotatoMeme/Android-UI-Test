package com.potatomeme.screen.planfit.presentation.on_board.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.screen.planfit.data.model.PlanfitLoginType
import com.potatomeme.screen.planfit.domain.usecase.GetPlanfitLoginTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanfitSplashViewModel @Inject constructor(
    private val getPlanfitLoginTypeUseCase: GetPlanfitLoginTypeUseCase,
) : ViewModel() {
    private val _isLoginChanel = Channel<LoginEvent>(Channel.BUFFERED)
    val isLoginFlow: Flow<LoginEvent> = _isLoginChanel.receiveAsFlow()

    fun checkLoginState() = viewModelScope.launch {
        _isLoginChanel.send(LoginEvent.UnChecked)
        delay(3000L)
        val loginType = getPlanfitLoginTypeUseCase()
        Log.d(TAG, "checkLoginState: ${loginType.name}")
        _isLoginChanel.send(LoginEvent.Login(loginType))
        if (loginType == PlanfitLoginType.None)
            _isLoginChanel.send(LoginEvent.UnChecked)
    }

    companion object {
        private const val TAG = "PlanfitSplashViewModel"
    }
}

sealed class LoginEvent {
    data object UnChecked : LoginEvent()
    data class Login(
        val loginType: PlanfitLoginType,
    ) : LoginEvent()
}


