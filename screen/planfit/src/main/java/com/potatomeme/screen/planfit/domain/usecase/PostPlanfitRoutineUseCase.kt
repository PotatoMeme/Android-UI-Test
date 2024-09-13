package com.potatomeme.screen.planfit.domain.usecase

import com.potatomeme.screen.planfit.data.model.Routine
import com.potatomeme.screen.planfit.data.model.UserInfo

interface PostPlanfitRoutineUseCase {
    suspend operator fun invoke(routine: Routine)
}