package com.ironmeddie.notes.feature_note.presentation.notes

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ironmeddie.notes.feature_note.presentation.notes.components.NoteItem
import com.ironmeddie.notes.feature_note.presentation.notes.components.OrderSection
import com.ironmeddie.notes.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    
    Scaffold(
        floatingActionButton = {
        FloatingActionButton(onClick = { navController.navigate(Screen.AddNoteScreen.route)}, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
        }
    }, scaffoldState = scaffoldState) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Your notes", style = MaterialTheme.typography.h4)
                IconButton(onClick = {
                    viewModel.onEvent(NotesEvent.ToogleOrderSection) }) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription ="Sort" )
                }
            }
            AnimatedVisibility(visible = state.isOrderSectionVisible,
            enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp), noteOrder = state.noteOrder){
                    Log.d("lambda started", "lambda started order")
                    viewModel.onEvent(NotesEvent.Order(it))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxWidth()){
                items(state.notes){ note->
                    NoteItem(note = note, modifier = Modifier.fillMaxWidth().padding(vertical =5.dp).clickable {
                        navController.navigate(Screen.AddNoteScreen.route + "?noteId=${note.id}&noteColor=${note.color}")
                    }) {
                        viewModel.onEvent(NotesEvent.DeleteNote(note))
                        scope.launch {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Note deleted",
                                actionLabel = "Undo"
                            )
                            if (result == SnackbarResult.ActionPerformed){
                                viewModel.onEvent(NotesEvent.RestoreNoute)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

    }
}