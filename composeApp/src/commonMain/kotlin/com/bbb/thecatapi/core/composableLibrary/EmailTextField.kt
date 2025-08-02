package com.bbb.thecatapi.core.composableLibrary

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fac23.kmp.getColorTheme

@Composable
fun EmailTextField(
    initEmail: String = "",
    onTextChange: (String) -> Unit
) {
    var textUser by remember { mutableStateOf("") }
    val colors = getColorTheme()

    textUser = initEmail

    OutlinedTextField(
        value = textUser,
        shape = RoundedCornerShape(12.dp),
        onValueChange = { newValue ->
            var valueFiltered = newValue.filter { !it.isWhitespace() }
            valueFiltered = valueFiltered.lowercase()
            textUser = valueFiltered
            onTextChange(textUser)
        },
        label = { Text("Correo electrónico", fontWeight = FontWeight.SemiBold) },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {

            IconButton(onClick = {}) {
                Icon(
                    modifier = Modifier.height(28.dp),
                    imageVector = Icons.Default.Email,
                    tint = colors.primary,
                    contentDescription = ""
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        textStyle = TextStyle(
            color = colors.textColor,
            fontSize = 16.sp
        ),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = colors.fieldActive,
            unfocusedIndicatorColor = colors.fieldInactive,
            disabledIndicatorColor = Color.Transparent,
            focusedLabelColor = colors.textMainColor,
            focusedLeadingIconColor = colors.textMainColor,
            unfocusedLabelColor = colors.textColor,
            unfocusedLeadingIconColor = colors.textMainColor,
            focusedContainerColor = colors.backgroundColor,
            unfocusedContainerColor = colors.backgroundColor,
        )
    )
}