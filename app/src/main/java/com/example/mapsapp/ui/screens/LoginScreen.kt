package com.example.mapsapp.ui.screens

import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.ui.navigation.Destination
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModel
import com.example.mapsapp.viewmodels.AuthViewModelFactory
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mapsapp.ui.navigation.Destination.Register

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onRegisterClick: () -> Unit) {
    val context = LocalContext.current

    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(SharedPreferencesHelper(context))
    )

    val authState by viewModel.authState.observeAsState()
    val showError by viewModel.showError.observeAsState(false)

    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")

    // Навигация при успешном входе
    if (authState == AuthState.Authenticated) {
        onLoginSuccess()
    } else {
        if (showError) {
            val errorMessage = (authState as? AuthState.Error)?.message ?: ""
            if (errorMessage.contains("invalid_credentials")) {
                Toast.makeText(context, "Credenciales inválidas", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_LONG).show()
            }
            viewModel.errorMessageShowed()
        }

        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun validateAndLogin() {
            when {
                email.isBlank() || password.isBlank() -> {
                    Toast.makeText(context, "Correo y contraseña son obligatorios", Toast.LENGTH_LONG).show()
                }
                !isEmailValid(email) -> {
                    Toast.makeText(context, "Correo inválido", Toast.LENGTH_LONG).show()
                }
                password.length < 6 -> {
                    Toast.makeText(context, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_LONG).show()
                }
                else -> {
                    viewModel.signIn()
                }
            }
        }

        // UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Iniciar sesión", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { viewModel.editEmail(it) },
                label = { Text("Correo electrónico") },
                placeholder = { Text("ejemplo@gmail.com") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { viewModel.editPassword(it) },
                label = { Text("Contraseña") },
                placeholder = { Text("123456") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { validateAndLogin() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onRegisterClick) {
                Text("¿No tienes una cuenta? Regístrate")
            }
        }
    }
}
