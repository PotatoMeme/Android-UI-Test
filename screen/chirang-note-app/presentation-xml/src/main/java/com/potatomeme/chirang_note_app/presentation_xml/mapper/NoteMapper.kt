package com.potatomeme.chirang_note_app.presentation_xml.mapper

import com.potatomeme.chirang_note_app.domain.entity.NoteEntity
import com.potatomeme.chirang_note_app.presentation_xml.model.ParcelableNote

object NoteMapper {
    fun NoteEntity.toParcelable(): ParcelableNote {
        return ParcelableNote(
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

    fun ParcelableNote.toDomain(): NoteEntity {
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