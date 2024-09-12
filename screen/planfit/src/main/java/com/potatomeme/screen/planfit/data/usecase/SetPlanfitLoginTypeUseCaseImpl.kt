package com.potatomeme.screen.planfit.data.usecase

import com.potatomeme.screen.planfit.data.model.PlanfitLoginType
import com.potatomeme.screen.planfit.data.source.local.PlanfitPreferencesDataStore
import com.potatomeme.screen.planfit.domain.usecase.SetPlanfitLoginTypeUseCase
import javax.inject.Inject

class SetPlanfitLoginTypeUseCaseImpl @Inject constructor(
    private val planfitPreferencesDataStore: PlanfitPreferencesDataStore
) : SetPlanfitLoginTypeUseCase {
    override suspend fun invoke(type: PlanfitLoginType) {
        planfitPreferencesDataStore.setPlanfitLoginType(type)
    }

}