package com.potatomeme.chirang_note_app.domain.usecase

import com.potatomeme.chirang_note_app.domain.entity.NoteEntity
import com.potatomeme.chirang_note_app.domain.repository.NoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(noteEntity: NoteEntity) {
        noteRepository.insertNote(noteEntity)
    }
}