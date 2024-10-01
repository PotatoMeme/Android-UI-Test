package com.potatomeme.chirang_note_app.domain.repository

import com.potatomeme.chirang_note_app.domain.entity.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface NoteRepository {
    fun getAllNotes(): Flow<List<NoteEntity>>
    suspend fun insertNote(noteEntity: NoteEntity)
    suspend fun deleteNote(noteEntity: NoteEntity)
}