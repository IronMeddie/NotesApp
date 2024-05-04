package com.ironmeddie.notes.feature_note.presentation.add_note

import androidx.compose.ui.focus.FocusState

sealed class AddNoteEvent{
    data class EnterContent(val value: String) : AddNoteEvent()
    data class ChangeFocusContent(val focusState: FocusState) : AddNoteEvent()
    data class ChangeColor(val color: Int) : AddNoteEvent()
    object SaveNote : AddNoteEvent()
}
