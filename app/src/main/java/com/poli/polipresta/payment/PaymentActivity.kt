package com.poli.polipresta.payment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.poli.polipresta.R
import com.poli.polipresta.data.FakeDataSource
import com.poli.polipresta.ui.theme.PoliprestaTheme

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoliprestaTheme {
                PaymentScreen(
                    currentDebt = FakeDataSource.currentDebt,
                    onBack = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    currentDebt: Double,
    viewModel: PaymentViewModel = viewModel(),
    onBack: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Header
        Text(
            text = stringResource(R.string.payment_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Estado actual
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.current_debt_label),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "$${"%.2f".format(currentDebt)}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de monto
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(stringResource(R.string.payment_amount)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//            leadingIcon = {
//                Icon(
//                    painter = painterResource(R.drawable.ic_payment),
//                    contentDescription = null
//                )
//            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de pago
        Button(
            onClick = { viewModel.processPayment(amount.toDoubleOrNull() ?: 0.0) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(stringResource(R.string.make_payment_button))
            }
        }

        // Mensajes de estado
        when {
            uiState.error != null -> {
                PaymentErrorMessage(message = uiState.error!!)
            }
            uiState.isSuccess -> {
                PaymentSuccessMessage(
                    amount = uiState.paymentAmount,
                    onBack = onBack
                )
            }
        }
    }
}

@Composable
private fun PaymentErrorMessage(message: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Icon(
//            painter = painterResource(R.drawable.ic_error),
//            contentDescription = "Error",
//            tint = MaterialTheme.colorScheme.error,
//            modifier = Modifier.size(48.dp)
//        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun PaymentSuccessMessage(amount: Double, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Icon(
//            painter = painterResource(R.drawable.ic_success),
//            contentDescription = "Éxito",
//            tint = MaterialTheme.colorScheme.primary,
//            modifier = Modifier.size(48.dp)
//        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.payment_success_message, amount),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text(stringResource(R.string.back_to_home))
        }
    }
}