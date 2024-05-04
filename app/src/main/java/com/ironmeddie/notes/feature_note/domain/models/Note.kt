package com.ironmeddie.notes.feature_note.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ironmeddie.notes.feature_note.presentation.ui.theme.BabyBlue
import com.ironmeddie.notes.feature_note.presentation.ui.theme.LightGreen
import com.ironmeddie.notes.feature_note.presentation.ui.theme.RedOrange
import com.ironmeddie.notes.feature_note.presentation.ui.theme.RedPink
import com.ironmeddie.notes.feature_note.presentation.ui.theme.Violet

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val content: String,
    val color: Int,
    val timestamp: Long,
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteExeption(message: String = INVALID_NOTE) : Exception(message) {
    companion object {
        const val INVALID_NOTE: String = "invalid note"
        const val OTHER_ERROR: String = "other error"
    }
}
