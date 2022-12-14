package com.ironmeddie.notes.feature_note.presentation.util

sealed class Screen(val route: String){
    object NotesScreen: Screen("notes_screen")
    object AddNoteScreen: Screen("add_note_scren")
}
