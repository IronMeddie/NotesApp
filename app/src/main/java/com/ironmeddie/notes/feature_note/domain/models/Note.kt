package com.ironmeddie.notes.feature_note.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ironmeddie.notes.feature_note.presentation.ui.theme.*

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long,
){
    companion object{
        val noteColors = listOf(RedOrange, LightGreen,Violet, BabyBlue, RedPink)
    }
}

class InvalidNoteExeption(message: String): Exception(message)