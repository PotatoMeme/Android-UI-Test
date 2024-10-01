package com.potatomeme.chirang_note_app.presentation_xml.create_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.chirang_note_app.domain.usecase.DeleteNoteUseCase
import com.potatomeme.chirang_note_app.domain.usecase.InsertNoteUseCase
import com.potatomeme.chirang_note_app.presentation_xml.mapper.NoteMapper.toDomain
import com.potatomeme.chirang_note_app.presentation_xml.model.ParcelableNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CNACreateNoteViewModel @Inject constructor(
    private val insertNoteUseCase: InsertNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
) : ViewModel() {

    private val _selectedNoteColor: MutableStateFlow<Int> = MutableStateFlow(0)
    val selectedNoteColor: StateFlow<Int> = _selectedNoteColor.asStateFlow()

    private val _selectedImagePath: MutableStateFlow<String> = MutableStateFlow("")
    val selectedImagePath: StateFlow<String> = _selectedImagePath.asStateFlow()

    private val _selectedWebURL: MutableStateFlow<String> = MutableStateFlow("")
    val selectedWebURL: StateFlow<String> = _selectedWebURL.asStateFlow()

    private val _imageChanged: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val imageChanged: StateFlow<Boolean> = _imageChanged.asStateFlow()

    fun whenIntentToNote(note: ParcelableNote) {
        if (selectedNoteColor.value == 0 && note.color != null && getColorIndex(note.color) != 0)
            _selectedNoteColor.value = getColorIndex(note.color)
        if (selectedImagePath.value == "" && note.imagePath != null && note.imagePath != "")
            _selectedImagePath.value = note.imagePath
        if (selectedWebURL.value == "" && note.webLink != null && note.webLink != "")
            _selectedWebURL.value = note.webLink
    }

    fun setSelectedNoteColor(idx:Int) {
        _selectedNoteColor.value = idx
    }

    private fun getColorIndex(color: String): Int {
        return CNACreateNoteActivity.COLOR_CODE_LIST.indexOf(color)
    }

    fun setSelectedImagePath(path: String) {
        _selectedImagePath.value = path
        _imageChanged.value = true
    }

    fun setSelectedWebURL(url: String) {
        _selectedWebURL.value = url
    }

    fun insertNote(note: ParcelableNote) = viewModelScope.launch {
        insertNoteUseCase.invoke(note.toDomain())
    }

    fun deleteNote(note: ParcelableNote) = viewModelScope.launch {
        deleteNoteUseCase.invoke(note.toDomain())
    }
}