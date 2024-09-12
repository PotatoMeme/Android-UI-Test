package com.potatomeme.screen.planfit.domain.usecase

import com.potatomeme.screen.planfit.data.model.Routine

interface RequestPlanfitRoutineListUseCase {
    suspend operator fun invoke() : List<Routine>
}