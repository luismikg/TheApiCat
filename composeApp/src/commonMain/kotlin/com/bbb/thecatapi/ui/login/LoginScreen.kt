package com.bbb.thecatapi.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bbb.thecatapi.core.composableLibrary.EmailTextField
import com.bbb.thecatapi.core.composableLibrary.PasswordTextField
import com.bbb.thecatapi.getColorTheme
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.catBreak
import kotlinproject.composeapp.generated.resources.catPattern
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onNextScreen: () -> Unit,
    onClickForgotPassword: () -> Unit,
    onClickRegister: () -> Unit
) {

    val colors = getColorTheme()

    Scaffold(
        bottomBar = {
            BodyFooter(
                onNextScreen = onNextScreen,
                onClickForgotPassword = onClickForgotPassword,
                onClickRegister = onClickRegister
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            Header(modifier = Modifier.weight(0.5f).background(colors.backgroundColor))
            Body(
                modifier = Modifier.weight(1f).background(colors.backgroundColor)
            )
        }
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Image(
            painter = painterResource(Res.drawable.catPattern),
            alpha = 1f,
            contentScale = ContentScale.None,
            contentDescription = "",
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(Res.drawable.catBreak),
                modifier = Modifier.size(300.dp),
                contentDescription = ""
            )
        }
    }
}

@Composable
private fun Body(modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Column(
            modifier = modifier.fillMaxSize().padding(
                top = 36.dp,
                bottom = 20.dp,
                start = 20.dp,
                end = 20.dp
            )
        ) {
            BodyHeader()
            BodyMain()
        }
    }
}

@Composable
private fun BodyHeader() {

    val colors = getColorTheme()

    Column {
        Text(
            text = "Bienvenido",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = colors.textSecondaryColor
        )

        Spacer(modifier = Modifier.size(12.dp))
        Text(
            text = "Muchos gatitos",
            fontSize = 14.sp,
            color = colors.textColor
        )
    }
}

@Composable
private fun BodyMain() {

    val viewModel = koinViewModel<LoginViewModel>()

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        EmailTextField(initEmail = viewModel.user.value) { email ->
            viewModel.user.value = email.lowercase().trim()
            viewModel.isEnableContinueButton()
        }
        Spacer(Modifier.height(16.dp))
        PasswordTextField(initPassword = viewModel.password.value) { password ->
            viewModel.password.value = password
            viewModel.isEnableContinueButton()
        }
        Spacer(Modifier.height(10.dp))

        val textStyle = TextStyle(
            fontSize = 10.sp,
            fontWeight = FontWeight.Light,
            lineHeight = 12.sp
        )
        Text(
            text = "• Al menos una minúscula + una mayúscula + un número + un símbolo.",
            style = textStyle
        )
        Text(text = "• Longitud mínima de 8 caracteres.", style = textStyle)

    }
}

@Composable
private fun BodyFooter(
    onNextScreen: () -> Unit,
    onClickForgotPassword: () -> Unit,
    onClickRegister: () -> Unit
) {

    val colors = getColorTheme()
    val viewModel = koinViewModel<LoginViewModel>()
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier.background(colors.backgroundColor)) {
        Button(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            enabled = state.isEnableContinue,
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.backgroundColor,
                contentColor = colors.textMainColor //Active
            ), onClick = {
                onNextScreen()
            }) {
            Text(
                text = "Continuar",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth().background(colors.backgroundColor),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            TextButton(onClick = { onClickForgotPassword() }) {
                Text(
                    text = "Olvidé la contraseña",
                    style = TextStyle(
                        color = colors.textColor,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Black
                    )
                )
            }

            TextButton(onClick = { onClickRegister() }) {
                Text(
                    text = "Creat tu cuenta",
                    style = TextStyle(
                        color = colors.textColor,
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Black
                    )
                )
            }
        }
        Spacer(Modifier.height(30.dp))
    }
}