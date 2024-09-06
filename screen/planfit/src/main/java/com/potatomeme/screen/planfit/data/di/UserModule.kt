package com.potatomeme.screen.planfit.data.di

import com.potatomeme.screen.planfit.data.usecase.GetPlanfitLoginTypeImpl
import com.potatomeme.screen.planfit.data.usecase.SetPlanfitLoginTypeImpl
import com.potatomeme.screen.planfit.domain.usecase.GetPlanfitLoginType
import com.potatomeme.screen.planfit.domain.usecase.SetPlanfitLoginType
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    abstract fun bindGetPlanfitLoginType(uc: GetPlanfitLoginTypeImpl): GetPlanfitLoginType

    @Binds
    abstract fun bindSetPlanfitLoginType(uc: SetPlanfitLoginTypeImpl): SetPlanfitLoginType
}