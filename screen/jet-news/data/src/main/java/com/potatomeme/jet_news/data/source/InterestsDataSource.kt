package com.potatomeme.jet_news.data.source

import com.potatomeme.jet_news.domain.entity.InterestSection
import javax.inject.Inject

class InterestsDataSource @Inject constructor() {
    val topics = listOf(
        InterestSection("Android", listOf("Jetpack Compose", "Kotlin", "Jetpack")),
        InterestSection(
            "Programming",
            listOf("Kotlin", "Declarative UIs", "Java", "Unidirectional Data Flow", "C++")
        ),
        InterestSection("Technology", listOf("Pixel", "Google"))
    )

    val people = listOf(
        "Kobalt Toral",
        "K'Kola Uvarek",
        "Kris Vriloc",
        "Grala Valdyr",
        "Kruel Valaxar",
        "L'Elij Venonn",
        "Kraag Solazarn",
        "Tava Targesh",
        "Kemarrin Muuda"
    )

    val publications = listOf(
        "Kotlin Vibe",
        "Compose Mix",
        "Compose Breakdown",
        "Android Pursue",
        "Kotlin Watchman",
        "Jetpack Ark",
        "Composeshack",
        "Jetpack Point",
        "Compose Tribune"
    )
}