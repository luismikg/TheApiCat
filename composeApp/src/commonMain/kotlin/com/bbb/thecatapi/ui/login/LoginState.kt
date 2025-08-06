package com.bbb.thecatapi.ui.login

data class LoginState(
    val isEnableContinue: Boolean = false,
)

sealed class LoginEvent {
    object Success : LoginEvent()
    object Failure : LoginEvent()
}
