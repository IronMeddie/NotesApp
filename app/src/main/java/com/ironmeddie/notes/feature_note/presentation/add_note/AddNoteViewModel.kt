package com.ironmeddie.notes.feature_note.presentation.add_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironmeddie.notes.feature_note.domain.models.InvalidNoteExeption
import com.ironmeddie.notes.feature_note.domain.models.Note
import com.ironmeddie.notes.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _content = mutableStateOf(
        NoteTextFieldState()
    )
    val content: State<NoteTextFieldState> = _content

    private val _color = mutableStateOf(Note.noteColors[0].toArgb())
    val color: State<Int> = _color

    private val _eventFLow = MutableSharedFlow<UiEvent>()
    val eventFLow = _eventFLow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let {
            if (it != -1) {
                viewModelScope.launch {
                    noteUseCases.getNoteByIdUseCase(it)?.also { note ->
                        currentNoteId = note.id
                        _content.value =
                            content.value.copy(text = note.content, isHintVisible = false)
                        _color.value = note.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddNoteEvent) {
        when (event) {
            is AddNoteEvent.EnterContent -> {
                _content.value = content.value.copy(text = event.value)
            }

            is AddNoteEvent.ChangeFocusContent -> {
                _content.value =
                    content.value.copy(isHintVisible = !event.focusState.isFocused && _content.value.text.isBlank())
            }

            is AddNoteEvent.ChangeColor -> {
                _color.value = event.color
            }

            is AddNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNoteUseCase(
                            Note(
                                content = content.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = color.value,
                                id = currentNoteId
                            )
                        )
                        AppMetrica.reportEvent("New Note")
                        _eventFLow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteExeption) {
                        _eventFLow.emit(
                            UiEvent.ShowSnackBar(e.message ?: InvalidNoteExeption.OTHER_ERROR)
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val code: String) : UiEvent()
        object SaveNote : UiEvent()

    }
}
