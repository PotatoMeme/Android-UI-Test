package com.potatomeme.chirang_note_app.data.repository

import com.potatomeme.chirang_note_app.data.dao.NoteDao
import com.potatomeme.chirang_note_app.data.mapper.NoteMapper.toData
import com.potatomeme.chirang_note_app.data.mapper.NoteMapper.toDomain
import com.potatomeme.chirang_note_app.domain.entity.NoteEntity
import com.potatomeme.chirang_note_app.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
) : NoteRepository {
    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return flow {
            noteDao.getAllNotes().collect { notes ->
                emit(notes.map { note ->
                    note.toDomain()
                })
            }
        }
    }

    override suspend fun insertNote(noteEntity: NoteEntity) {
        noteDao.insertNote(noteEntity.toData())
    }

    override suspend fun deleteNote(noteEntity: NoteEntity) {
        noteDao.deleteNote(noteEntity.toData())
    }
}