package com.potatomeme.chirang_note_app.presentation_xml.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.potatomeme.chirang_note_app.domain.usecase.DeleteNoteUseCase
import com.potatomeme.chirang_note_app.domain.usecase.GetNotesUseCase
import com.potatomeme.chirang_note_app.domain.usecase.InsertNoteUseCase
import com.potatomeme.chirang_note_app.presentation_xml.mapper.NoteMapper.toDomain
import com.potatomeme.chirang_note_app.presentation_xml.mapper.NoteMapper.toParcelable
import com.potatomeme.chirang_note_app.presentation_xml.model.ParcelableNote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CNAMainViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
) : ViewModel() {
    private val _storedNotes = MutableStateFlow<List<ParcelableNote>>(emptyList())
    private val storedNotes: StateFlow<List<ParcelableNote>> = _storedNotes.asStateFlow()

    private val _searchedResult = MutableStateFlow<SearchedResult>(SearchedResult.Init)
    val searchedResult: StateFlow<SearchedResult> = _searchedResult.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                getNotesUseCase().collect { notes ->
                    _storedNotes.value = notes.map { note ->
                        note.toParcelable()
                    }
                }
            }
            launch {
                storedNotes.collectLatest { notes ->
                    _searchedResult.value = when (searchedResult.value) {
                        SearchedResult.Init -> SearchedResult.Success("", notes)
                        is SearchedResult.Success -> {
                            val query = (searchedResult.value as SearchedResult.Success).query
                            SearchedResult.Success(
                                query,
                                notes.search(query)
                            )
                        }
                    }
                }
            }
        }
    }

    fun search(query: String) {
        _searchedResult.value = SearchedResult.Success(query, storedNotes.value.search(query))
    }

    private fun List<ParcelableNote>.search(query: String): List<ParcelableNote> {
        return filter { note ->
            note.title?.lowercase()?.contains(query.lowercase()) == true
                    || note.subtitle?.lowercase()?.contains(query.lowercase()) == true
                    || note.noteText?.lowercase()?.contains(query.lowercase()) == true
        }
    }
}

sealed class SearchedResult {
    data class Success(val query: String, val notes: List<ParcelableNote>) : SearchedResult()
    data object Init : SearchedResult()
}