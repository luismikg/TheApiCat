package com.bbb.thecatapi.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbb.thecatapi.domain.RepositoryDataBase
import com.bbb.thecatapi.ui.core.utils.isEmail
import com.bbb.thecatapi.ui.core.utils.isValidPassword
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val repositoryDataBase: RepositoryDataBase) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _loginSuccess = Channel<LoginEvent>()
    val loginSuccess = _loginSuccess.receiveAsFlow()

    var user = mutableStateOf("")
    var password = mutableStateOf("")

    fun isEnableContinueButton() {
        _state.update { s ->
            s.copy(
                isEnableContinue = isValidEmail() && isValidPassword()
            )
        }
    }

    fun getToken(): String {
        val result = user.value.map {
            it.code
        }.joinToString("")
        return result
    }

    fun makeLogin(token: String) {
        viewModelScope.launch {
            repositoryDataBase.deleteSession()
            repositoryDataBase.upsertSession(token = token)

            _loginSuccess.send(LoginEvent.Success)
        }

        user.value = ""
        password.value = ""
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
