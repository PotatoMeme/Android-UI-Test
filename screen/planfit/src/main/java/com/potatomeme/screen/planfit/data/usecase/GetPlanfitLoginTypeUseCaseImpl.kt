package com.potatomeme.screen.planfit.data.usecase

import com.potatomeme.screen.planfit.data.model.PlanfitLoginType
import com.potatomeme.screen.planfit.data.source.PlanfitPreferencesDataStore
import com.potatomeme.screen.planfit.domain.usecase.GetPlanfitLoginTypeUseCase
import javax.inject.Inject

class GetPlanfitLoginTypeUseCaseImpl @Inject constructor(
    private val planfitPreferencesDataStore: PlanfitPreferencesDataStore
) : GetPlanfitLoginTypeUseCase{
    override suspend fun invoke(): PlanfitLoginType {
        return planfitPreferencesDataStore.getPlanfitLoginType()
    }
}