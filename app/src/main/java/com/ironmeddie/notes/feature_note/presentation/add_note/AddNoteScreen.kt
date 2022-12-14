package com.ironmeddie.notes.feature_note.presentation.add_note

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ironmeddie.notes.feature_note.domain.models.Note
import com.ironmeddie.notes.feature_note.presentation.add_note.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddNoteViewModel = hiltViewModel()
) {

    val titleState = viewModel.title.value
    val contentState = viewModel.content.value

    val scaffoldState= rememberScaffoldState()
    val noteBackground = remember {
        Animatable(Color(if (noteColor != -1) noteColor else viewModel.color.value))
    }
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(key1 = true){
        viewModel.eventFLow.collectLatest { event->
            when(event){
                is AddNoteViewModel.UiEvent.ShowSnackBar ->{
                    scaffoldState.snackbarHostState.showSnackbar(event.message)
                }
                is AddNoteViewModel.UiEvent.SaveNote->{
                    navController.navigateUp()
                }
            }

        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {  viewModel.onEvent(AddNoteEvent.SaveNote)}, backgroundColor = MaterialTheme.colors.primary) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
            }
        },
        scaffoldState = scaffoldState
    ) {
        
        Column(modifier = Modifier
            .fillMaxSize()
            .background(noteBackground.value)
            .padding(it)
            .padding(16.dp)) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
                Note.noteColors.forEach { color->

                    Box(modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .background(color)
                        .clip(
                            CircleShape
                        )
                        .border(
                            3.dp,
                            color = if (viewModel.color.value == color.toArgb()) Color.Black else Color.Transparent,
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                noteBackground.animateTo(
                                    color,
                                    animationSpec = tween(500)
                                )
                            }
                            viewModel.onEvent(AddNoteEvent.ChangeColor(color.toArgb()))
                        })
                }

            }

            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {viewModel.onEvent(AddNoteEvent.EnterTitle(it))} ,
                onFocusChange = {viewModel.onEvent(AddNoteEvent.ChangeFocusTitle(it))},
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
                )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {viewModel.onEvent(AddNoteEvent.EnterContent(it))} ,
                onFocusChange = {viewModel.onEvent(AddNoteEvent.ChangeFocusContent(it))},
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }

    }
}