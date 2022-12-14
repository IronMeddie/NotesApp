package com.ironmeddie.notes.feature_note.presentation.notes

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.notes.feature_note.domain.models.Note
import com.ironmeddie.notes.feature_note.domain.use_case.NoteUseCases
import com.ironmeddie.notes.feature_note.domain.util.NoteOrder
import com.ironmeddie.notes.feature_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val useCases: NoteUseCases): ViewModel() {

    private val _state= mutableStateOf(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null

    private var getNotesJob: Job? = null


    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.Order->{
                Log.d("LogCheck", "Order Started")
                    if (state.value.noteOrder::class == event.noteOrder::class &&
                            state.value.noteOrder.orderType == event.noteOrder.orderType){
                        Log.d("LogCheck", "Order is the same")
                        return
                    }else{
                        Log.d("LogCheck", "Order is not the same")
                        getNotes(event.noteOrder)
                    }

            }
            is NotesEvent.DeleteNote->{
                viewModelScope.launch {
                    useCases.deleteNoteUseCase(event.note)
                    recentlyDeletedNote = event.note
                }

            }
            is NotesEvent.RestoreNoute->{
                viewModelScope.launch {
                    useCases.addNoteUseCase(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is NotesEvent.ToogleOrderSection->{
                _state.value = state.value.copy(
                    isOrderSectionVisible = !_state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder){
        Log.d("LogCheck", "getting notes")
        getNotesJob?.cancel()
        getNotesJob = useCases.getNotesUseCase(noteOrder).onEach { notes ->
            _state.value = state.value.copy(notes = notes, noteOrder = noteOrder)
            Log.d("LogCheck", "value updated")
        }.launchIn(viewModelScope)
    }



}