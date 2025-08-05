package com.bbb.thecatapi.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bbb.thecatapi.ui.core.utils.isEmail
import com.bbb.thecatapi.ui.core.utils.isValidPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel() : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    var user = mutableStateOf("")
    var password = mutableStateOf("")

    fun isEnableContinueButton() {
        _state.update { s ->
            s.copy(
                isEnableContinue = isValidEmail() && isValidPassword()
            )
        }
    }

    private fun isValidEmail(): Boolean {
        return user.value.isEmail()
    }

    private fun isValidPassword(): Boolean {
        return password.value.isValidPassword()
    }
}
