package com.apap.cameraManager.presentation.view

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.apap.cameraManager.R

@Composable
fun SearchBar(modifier: Modifier, value: String, onValueChange: (String) -> Unit, onDone: KeyboardActionScope.() -> Unit) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 30.dp),
        label = { SearchBarLabel() },
        singleLine = true,
        colors = setSearchBarColors(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = onDone,
        ),
    )
}

@Composable
fun SearchBarLabel() {
    Text(
        text = stringResource(R.string.search_bar_label),
        color = if (isSystemInDarkTheme()) Color.White else Color.Black
    )
}

@Composable
fun setSearchBarColors(): TextFieldColors {
    val color = getThemeForColor(isSystemInDarkTheme())
    return TextFieldDefaults.outlinedTextFieldColors(
        cursorColor = color,
        textColor = color,
        focusedBorderColor = color,
        unfocusedBorderColor = color,
    )
}

fun getThemeForColor(isDarkTheme: Boolean) = if (isDarkTheme) Color.White else Color.Black