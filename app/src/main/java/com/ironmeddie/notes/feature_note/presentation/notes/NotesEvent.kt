package com.ironmeddie.notes.feature_note.presentation.notes


import com.ironmeddie.notes.feature_note.domain.models.Note
import com.ironmeddie.notes.feature_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object ToogleSearchSection : NotesEvent()
    object RestoreNoute : NotesEvent()
    object ToogleOrderSection : NotesEvent()
    data class Search(val query: String) : NotesEvent()
}
