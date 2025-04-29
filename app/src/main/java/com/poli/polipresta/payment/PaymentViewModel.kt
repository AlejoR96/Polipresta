package com.poli.polipresta.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poli.polipresta.data.FakeDataSource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PaymentViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState = _uiState.asStateFlow()

    fun processPayment(amount: Double) {
        if (amount <= 0) {
            _uiState.value = PaymentUiState(error = "Monto inválido")
            return
        }

        if (amount > FakeDataSource.currentDebt) {
            _uiState.value = PaymentUiState(error = "El pago excede la deuda actual")
            return
        }

        _uiState.value = PaymentUiState(isLoading = true)

        viewModelScope.launch {
            try {
                delay(1500) // Simulación de proceso

                FakeDataSource.currentDebt -= amount
                // FakeDataSource.paymentHistory.add(amount)

                _uiState.value = PaymentUiState(
                    isSuccess = true,
                    paymentAmount = amount
                )
            } catch (e: Exception) {
                _uiState.value = PaymentUiState(error = "Error al procesar el pago")
            }
        }
    }
}

data class PaymentUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val paymentAmount: Double = 0.0
)