package com.potatomeme.screen.planfit.domain.usecase

import com.potatomeme.screen.planfit.data.model.Gym

interface RequestGymListUseCase {
    suspend operator fun invoke(keyword: String) : List<Gym>
}