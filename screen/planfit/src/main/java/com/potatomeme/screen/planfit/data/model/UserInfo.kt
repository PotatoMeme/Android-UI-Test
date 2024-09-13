package com.potatomeme.screen.planfit.data.model

import com.potatomeme.screen.planfit.presentation.on_board.select_info.SelectInfo

data class UserInfo(
    val level: Int,
    val place: Int,
    val gym: Gym? = null,
    val equipmentType: Int? = null,
    val times: Int,
    val bodyGoal: List<Int>,
    val sex: SelectInfo,
    val birth: String,
    val weight: String,
    val height: String,
    val route: SelectInfo,
)
