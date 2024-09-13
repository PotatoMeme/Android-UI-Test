package com.potatomeme.screen.planfit.data.usecase

import com.potatomeme.screen.planfit.data.model.Routine
import com.potatomeme.screen.planfit.domain.usecase.RequestPlanfitRoutineListUseCase
import javax.inject.Inject

class RequestPlanfitRoutineListUseCaseImpl @Inject constructor() : RequestPlanfitRoutineListUseCase {
    override suspend operator fun invoke(): List<Routine> {
        return mutableListOf<Routine>().apply {
            repeat(3) {
                add(Routine("루틴 ${it + 1}"))
            }
        }
    }
}