package com.potatomeme.screen.planfit.data.usecase

import com.potatomeme.screen.planfit.data.model.PlanfitLoginType
import com.potatomeme.screen.planfit.data.model.UserInfo
import com.potatomeme.screen.planfit.data.source.PlanfitPreferencesDataStore
import com.potatomeme.screen.planfit.domain.usecase.PostPlanfitUserInfoUseCase
import com.potatomeme.screen.planfit.domain.usecase.SetPlanfitLoginTypeUseCase
import javax.inject.Inject

class PostPlanfitUserInfoUseCaseImpl @Inject constructor() : PostPlanfitUserInfoUseCase {
    override suspend fun invoke(userInfo: UserInfo) {
        //todo post user info
    }

}