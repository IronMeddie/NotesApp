package com.ironmeddie.notes.feature_note.data.repository

import com.ironmeddie.notes.feature_note.data.data_source.NoteDao
import com.ironmeddie.notes.feature_note.domain.models.Note
import com.ironmeddie.notes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val dao: NoteDao) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNoteByid(id: Int): Note? {
        return dao.getNoteById(id)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }


}