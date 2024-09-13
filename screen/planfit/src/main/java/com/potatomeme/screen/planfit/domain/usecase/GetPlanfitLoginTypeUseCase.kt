package com.potatomeme.screen.planfit.domain.usecase

import com.potatomeme.screen.planfit.data.model.PlanfitLoginType

interface GetPlanfitLoginTypeUseCase {
    suspend operator fun invoke() : PlanfitLoginType
}