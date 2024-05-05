package com.ironmeddie.notes.feature_note.presentation.add_note.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun TransparentHintTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
    requestFocus: Boolean = true
) {
    Box(modifier = modifier) {
        val focusRequester = remember { FocusRequester() }
        BasicTextField(
            onValueChange = { value: TextFieldValue ->
                onValueChange(value.text)
            },
            value = TextFieldValue(text, TextRange(text.length)),
            singleLine = singleLine,
            textStyle = textStyle.copy(color = MaterialTheme.colors.onBackground),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged {
                    onFocusChange(it)
                },
        )
        if (isHintVisible) {
            Text(text = hint, style = textStyle, color = MaterialTheme.colors.onBackground)
        }
        if (requestFocus) {
            LaunchedEffect(key1 = true) {
                focusRequester.requestFocus()
            }
        }
    }
}
