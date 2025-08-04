package com.bbb.thecatapi.core.composableLibrary

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bbb.thecatapi.getColorTheme

@Composable
fun PasswordTextField(
    initPassword: String = "",
    label: String = "Contraseña",
    isError: Boolean = false,
    onTextChange: (String) -> Unit
) {
    var textPassword by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    val colors = getColorTheme()

    textPassword = initPassword

    OutlinedTextField(
        value = textPassword,
        shape = RoundedCornerShape(12.dp),
        onValueChange = {
            if (it.length <= 20) {
                textPassword = it
                onTextChange(textPassword)
            }
        },
        isError = isError,
        label = { Text(label, fontWeight = FontWeight.SemiBold) },
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = passwordVisible.not() }) {
                Icon(
                    imageVector =
                        if (passwordVisible) Icons.Default.Visibility
                        else Icons.Default.VisibilityOff,
                    tint = colors.primary,
                    contentDescription = ""
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
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