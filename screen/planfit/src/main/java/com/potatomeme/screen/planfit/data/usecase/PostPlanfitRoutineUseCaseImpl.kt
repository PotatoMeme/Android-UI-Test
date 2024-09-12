package com.potatomeme.screen.planfit.data.usecase

import com.potatomeme.screen.planfit.data.model.Routine
import com.potatomeme.screen.planfit.data.model.UserInfo
import com.potatomeme.screen.planfit.domain.usecase.PostPlanfitRoutineUseCase
import com.potatomeme.screen.planfit.domain.usecase.PostPlanfitUserInfoUseCase
import javax.inject.Inject

class PostPlanfitRoutineUseCaseImpl @Inject constructor() : PostPlanfitRoutineUseCase {
    override suspend fun invoke(routine: Routine) {
        //todo post user info
    }
}