package com.potatomeme.chirang_note_app.domain.entity

data class NoteEntity(
    val id: Int,
    val title: String?,
    val dateTime: String?,
    val subtitle: String?,
    val noteText: String?,
    val imagePath: String?,
    val color: String?,
    val webLink: String?,
)