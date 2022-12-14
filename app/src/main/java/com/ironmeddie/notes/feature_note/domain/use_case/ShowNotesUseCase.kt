package com.ironmeddie.notes.feature_note.domain.use_case

import com.ironmeddie.notes.feature_note.domain.models.Note
import com.ironmeddie.notes.feature_note.domain.repository.NoteRepository
import com.ironmeddie.notes.feature_note.domain.util.NoteOrder
import com.ironmeddie.notes.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetNotesUseCase(private val repository: NoteRepository) {
    operator fun invoke(noteOrder: NoteOrder ): Flow<List<Note>>{
        return repository.getNotes().map { notes->
            when(noteOrder.orderType){
                is OrderType.Ascending->{
                    when(noteOrder){
                        is NoteOrder.Title-> notes.sortedBy { it.title.lowercase() }
                        is NoteOrder.Color-> notes.sortedBy { it.color }
                        is NoteOrder.Date-> notes.sortedBy {it.timestamp }
                    }
                }
                is OrderType.Descending->{
                    when(noteOrder){
                        is NoteOrder.Title-> notes.sortedByDescending { it.title.lowercase() }
                        is NoteOrder.Color-> notes.sortedByDescending { it.color }
                        is NoteOrder.Date-> notes.sortedByDescending { it.timestamp }
                    }
                }
            }

        }
    }
}