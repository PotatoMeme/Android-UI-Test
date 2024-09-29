package com.potatomeme.chirang_note_app.presentation_xml.create_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.chirang_note_app.domain.usecase.DeleteNoteUseCase
import com.potatomeme.chirang_note_app.domain.usecase.InsertNoteUseCase
import com.potatomeme.chirang_note_app.presentation_xml.mapper.NoteMapper.toDomain
import com.potatomeme.chirang_note_app.presentation_xml.model.ParcelableNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CNACreateNoteViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
): ViewModel(){

    fun insertNote(note: ParcelableNote) = viewModelScope.launch {
        insertNoteUseCase.invoke(note.toDomain())
    }

    fun deleteNote(note: ParcelableNote) = viewModelScope.launch {
        deleteNoteUseCase.invoke(note.toDomain())
    }
}