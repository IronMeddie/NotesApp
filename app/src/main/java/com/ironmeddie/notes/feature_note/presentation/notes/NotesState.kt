package com.ironmeddie.notes.feature_note.presentation.notes

import com.ironmeddie.notes.feature_note.domain.models.Note
import com.ironmeddie.notes.feature_note.domain.util.NoteOrder
import com.ironmeddie.notes.feature_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val isSerachFieldVisible: Boolean = false,
    val searchQuery: String = "",
)
