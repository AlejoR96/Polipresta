package com.poli.polipresta.loan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poli.polipresta.data.FakeDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoanViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoanUiState())
    val uiState = _uiState.asStateFlow()

    fun requestLoan(amount: Double, description: String) {
        if (amount <= 0) {
            _uiState.value = LoanUiState(error = "Monto inválido")
            return
        }

        _uiState.value = LoanUiState(isLoading = true)

        viewModelScope.launch {
            try {
                // Simular solicitud a servidor
                delay(1500)
                FakeDataSource.currentDebt += amount
                _uiState.value = LoanUiState(isSuccess = true)
            } catch (e: Exception) {
                _uiState.value = LoanUiState(error = "Error al solicitar préstamo")
            }
        }
    }
}

data class LoanUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val approvedAmount: Double = FakeDataSource.currentDebt
)