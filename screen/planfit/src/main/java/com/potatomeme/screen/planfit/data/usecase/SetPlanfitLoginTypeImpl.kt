package com.potatomeme.screen.planfit.data.usecase

import com.potatomeme.screen.planfit.data.model.PlanfitLoginType
import com.potatomeme.screen.planfit.data.source.PlanfitPreferencesDataStore
import com.potatomeme.screen.planfit.domain.usecase.SetPlanfitLoginType
import javax.inject.Inject

class SetPlanfitLoginTypeImpl @Inject constructor(
    private val planfitPreferencesDataStore: PlanfitPreferencesDataStore
) : SetPlanfitLoginType {
    override suspend fun invoke(type: PlanfitLoginType) {
        planfitPreferencesDataStore.setPlanfitLoginType(type)
    }

}