package com.potatomeme.screen.planfit.domain.usecase

import com.potatomeme.screen.planfit.data.model.UserInfo

interface PostPlanfitUserInfoUseCase {
    suspend operator fun invoke(info: UserInfo)
}