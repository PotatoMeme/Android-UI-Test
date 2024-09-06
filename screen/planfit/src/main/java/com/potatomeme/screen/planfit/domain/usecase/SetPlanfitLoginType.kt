package com.potatomeme.screen.planfit.domain.usecase

import com.potatomeme.screen.planfit.data.model.PlanfitLoginType

interface SetPlanfitLoginType {
    suspend operator fun invoke(type:PlanfitLoginType)
}