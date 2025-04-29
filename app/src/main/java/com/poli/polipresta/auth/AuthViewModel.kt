package com.poli.polipresta.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poli.polipresta.data.FakeDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(cedula: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            try {
                // Simular retraso de red
                kotlinx.coroutines.delay(1500)

                val user = FakeDataSource.mockUsers.find {
                    it.cedula == cedula && it.password == password
                }

                if (user != null) {
                    FakeDataSource.currentUser = user
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error("Credenciales incorrectas")
                }
            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Error de conexión")
            }
        }
    }

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }
}