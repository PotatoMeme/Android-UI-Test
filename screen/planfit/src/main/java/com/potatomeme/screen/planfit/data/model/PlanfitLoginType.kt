package com.potatomeme.screen.planfit.data.model

enum class PlanfitLoginType(val num: Int) {
    None(0), KakaoTalk(1), Google(2), Facebook(3);

    companion object {
        fun fromType(num: Int): PlanfitLoginType {
            return entries.firstOrNull { it.num == num } ?: None
        }
    }
}