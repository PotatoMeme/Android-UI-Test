package com.potatomeme.chirang_note_app.data.mapper

import com.potatomeme.chirang_note_app.data.model.Note
import com.potatomeme.chirang_note_app.domain.entity.NoteEntity

object NoteMapper {
    fun NoteEntity.toData(): Note {
        return Note(
            id = id,
            title = title,
            dateTime = dateTime,
            subtitle = subtitle,
            noteText = noteText,
            imagePath = imagePath,
            color = color,
            webLink = webLink
        )
    }

    fun Note.toDomain(): NoteEntity {
        return NoteEntity(
            id = id,
            title = title,
            dateTime = dateTime,
            subtitle = subtitle,
            noteText = noteText,
            imagePath = imagePath,
            color = color,
            webLink = webLink
        )
    }
}