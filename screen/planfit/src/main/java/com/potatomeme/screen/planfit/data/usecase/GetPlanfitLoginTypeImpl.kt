package com.potatomeme.screen.planfit.data.usecase

import com.potatomeme.screen.planfit.data.model.PlanfitLoginType
import com.potatomeme.screen.planfit.data.source.PlanfitPreferencesDataStore
import com.potatomeme.screen.planfit.domain.usecase.GetPlanfitLoginType
import javax.inject.Inject

class GetPlanfitLoginTypeImpl @Inject constructor(
    private val planfitPreferencesDataStore: PlanfitPreferencesDataStore
) : GetPlanfitLoginType{
    override suspend fun invoke(): PlanfitLoginType {
        return planfitPreferencesDataStore.getPlanfitLoginType()
    }
}