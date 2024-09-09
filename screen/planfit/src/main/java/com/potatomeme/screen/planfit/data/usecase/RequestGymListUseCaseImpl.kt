package com.potatomeme.screen.planfit.data.usecase

import com.potatomeme.screen.planfit.data.model.Gym
import com.potatomeme.screen.planfit.domain.usecase.RequestGymListUseCase
import javax.inject.Inject

class RequestGymListUseCaseImpl @Inject constructor() : RequestGymListUseCase {
    override suspend operator fun invoke(keyword: String): List<Gym> {
        return mutableListOf<Gym>().apply {
            repeat(30) {
                add(Gym("헬스장 이름${it + 1}", "주소${it + 1}", String.format("%05d", it + 1)))
            }
        }
    }
}