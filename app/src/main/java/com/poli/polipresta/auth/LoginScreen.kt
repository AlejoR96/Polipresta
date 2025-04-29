package com.poli.polipresta.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.poli.polipresta.R
import com.poli.polipresta.utils.SessionManager
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var cedula by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Observar estado del ViewModel (sin cambios)
    LaunchedEffect(viewModel.loginState) {
        viewModel.loginState.collect { state ->
            when (state) {
                is AuthViewModel.LoginState.Loading -> isLoading = true
                is AuthViewModel.LoginState.Success -> {
                    isLoading = false
                    SessionManager.saveLoginState(context, true, cedula)
                    onLoginSuccess()
                }
                is AuthViewModel.LoginState.Error -> {
                    isLoading = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(state.message)
                    }
                }
                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo añadido aquí (sin afectar funcionalidad)
            Image(
                painter = painterResource(R.drawable.poli),
                contentDescription = "Logo Universidad",
                modifier = Modifier
                    .size(400.dp)
                    .padding(vertical = 32.dp),
                contentScale = ContentScale.Fit
            )

            // Contenedor del formulario (misma funcionalidad)
            Column(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .width(IntrinsicSize.Max),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Campo Cédula (sin cambios)
                OutlinedTextField(
                    value = cedula,
                    onValueChange = { cedula = it },
                    label = { Text(stringResource(R.string.hint_cedula)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    leadingIcon = {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_person),
//                            contentDescription = "Icono cédula"
//                        )
//                    }
                )

                // Campo Contraseña (sin cambios)
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(stringResource(R.string.hint_password)) },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
//                    leadingIcon = {
//                        Icon(
//                            painter = painterResource(R.drawable.ic_lock),
//                            contentDescription = "Icono contraseña"
//                        )
//                    }
                )

                // Botón de Login (sin cambios)
                Button(
                    onClick = { viewModel.login(cedula, password) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(stringResource(R.string.btn_login))
                    }
                }
            }

            // Texto institucional opcional
            Text(
                text = stringResource(R.string.institutional_footer),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}