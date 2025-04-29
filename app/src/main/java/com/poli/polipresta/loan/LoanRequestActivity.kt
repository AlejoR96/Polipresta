package com.poli.polipresta.loan

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
import com.poli.polipresta.ui.theme.PoliprestaTheme

class LoanRequestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoliprestaTheme {
                LoanRequestScreen(
                    onBack = { finish() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanRequestScreen(
    viewModel: LoanViewModel = viewModel(),
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Título y texto introductorio
        Text(
            text = stringResource(R.string.loan_request_title),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.loan_instructions),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Términos del préstamo
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.loan_terms),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Campos del formulario
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(stringResource(R.string.loan_amount)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
//                Icon(
//                    painter = painterResource(R.drawable.ic_money),
//                    contentDescription = null
//                )
            }
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(R.string.loan_description)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de solicitud
        Button(
            onClick = {
                viewModel.requestLoan(
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    description = description
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(stringResource(R.string.request_loan_button))
            }
        }

        // Mensajes de estado
        when {
            uiState.error != null -> {
                ErrorMessage(message = uiState.error!!)
            }
            uiState.isSuccess -> {
                SuccessMessage(
                    amount = amount.toDoubleOrNull() ?: 0.0,
                    onBack = onBack
                )
            }
        }
    }
}

@Composable
private fun ErrorMessage(message: String) {
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
private fun SuccessMessage(amount: Double, onBack: () -> Unit) {
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
            text = stringResource(R.string.loan_success_message, amount),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = onBack) {
            Text(stringResource(R.string.back_to_home))
        }
    }
}