package com.ironmeddie.notes.feature_note.domain.use_case

import com.ironmeddie.notes.feature_note.domain.models.Note
import com.ironmeddie.notes.feature_note.domain.repository.NoteRepository

class GetNoteByIdUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(id: Int): Note?{
       return repository.getNoteByid(id)
    }
}
