package com.potatomeme.screen.planfit.data.di

import com.potatomeme.screen.planfit.data.usecase.GetPlanfitLoginTypeUseCaseImpl
import com.potatomeme.screen.planfit.data.usecase.PostPlanfitRoutineUseCaseImpl
import com.potatomeme.screen.planfit.data.usecase.PostPlanfitUserInfoUseCaseImpl
import com.potatomeme.screen.planfit.data.usecase.RequestGymListUseCaseImpl
import com.potatomeme.screen.planfit.data.usecase.RequestPlanfitRoutineListUseCaseImpl
import com.potatomeme.screen.planfit.data.usecase.SetPlanfitLoginTypeUseCaseImpl
import com.potatomeme.screen.planfit.domain.usecase.GetPlanfitLoginTypeUseCase
import com.potatomeme.screen.planfit.domain.usecase.PostPlanfitRoutineUseCase
import com.potatomeme.screen.planfit.domain.usecase.PostPlanfitUserInfoUseCase
import com.potatomeme.screen.planfit.domain.usecase.RequestGymListUseCase
import com.potatomeme.screen.planfit.domain.usecase.RequestPlanfitRoutineListUseCase
import com.potatomeme.screen.planfit.domain.usecase.SetPlanfitLoginTypeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {
    @Binds
    abstract fun bindGetPlanfitLoginTypeUseCase(uc: GetPlanfitLoginTypeUseCaseImpl): GetPlanfitLoginTypeUseCase

    @Binds
    abstract fun bindSetPlanfitLoginTypeUseCase(uc: SetPlanfitLoginTypeUseCaseImpl): SetPlanfitLoginTypeUseCase

    @Binds
    abstract fun bindRequestGymListUseCase(uc: RequestGymListUseCaseImpl): RequestGymListUseCase

    @Binds
    abstract fun bindPostPlanfitUserInfoUseCase(uc: PostPlanfitUserInfoUseCaseImpl): PostPlanfitUserInfoUseCase

    @Binds
    abstract fun bindRequestPlanfitRoutineListUseCase(uc: RequestPlanfitRoutineListUseCaseImpl): RequestPlanfitRoutineListUseCase

    @Binds
    abstract fun bindPostPlanfitRoutineUseCase(uc: PostPlanfitRoutineUseCaseImpl): PostPlanfitRoutineUseCase
}