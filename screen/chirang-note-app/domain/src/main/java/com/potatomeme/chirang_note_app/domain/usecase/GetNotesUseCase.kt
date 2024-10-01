package com.potatomeme.chirang_note_app.domain.usecase

import com.potatomeme.chirang_note_app.domain.entity.NoteEntity
import com.potatomeme.chirang_note_app.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(): Flow<List<NoteEntity>> {
        return noteRepository.getAllNotes()
    }
}