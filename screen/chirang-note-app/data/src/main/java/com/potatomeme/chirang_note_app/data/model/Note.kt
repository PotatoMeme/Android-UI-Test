package com.potatomeme.chirang_note_app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String?,
    val dateTime: String?,
    val subtitle: String?,
    val noteText: String?,
    val imagePath: String?,
    val color: String?,
    val webLink: String?,
)