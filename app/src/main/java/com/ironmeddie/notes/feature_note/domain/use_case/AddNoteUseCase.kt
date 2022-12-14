package com.ironmeddie.notes.feature_note.domain.use_case

import com.ironmeddie.notes.feature_note.domain.models.InvalidNoteExeption
import com.ironmeddie.notes.feature_note.domain.models.Note
import com.ironmeddie.notes.feature_note.domain.repository.NoteRepository

class AddNoteUseCase(private val repository: NoteRepository) {

    @Throws(InvalidNoteExeption::class)
    suspend operator fun invoke(note: Note){
        if (note.title.isBlank()){
            throw InvalidNoteExeption("The title of the note can't be empty")
        }
        if (note.content.isBlank()){
            throw InvalidNoteExeption("The content of the note can't be empty")
        }
        repository.insertNote(note)
    }
}