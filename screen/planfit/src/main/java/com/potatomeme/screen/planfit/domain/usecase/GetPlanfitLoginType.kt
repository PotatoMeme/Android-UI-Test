package com.potatomeme.screen.planfit.domain.usecase

import com.potatomeme.screen.planfit.data.model.PlanfitLoginType

interface GetPlanfitLoginType {
    suspend operator fun invoke() : PlanfitLoginType
}