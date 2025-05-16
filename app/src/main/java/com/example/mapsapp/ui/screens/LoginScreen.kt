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

    // Navegar si login fue exitoso
    if (authState == AuthState.Authenticated) {
        onLoginSuccess()
    }

    if (showError) {
        val errorMessage = (authState as AuthState.Error).message
        if (errorMessage!!.contains("invalid_credentials")) {
            Toast.makeText(context, "Invalid credentials", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "An error has ocurred", Toast.LENGTH_LONG).show()
        }
        viewModel.errorMessageShowed()
    }



    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.editEmail(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.editPassword(it) },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { viewModel.signIn() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onRegisterClick) {
            Text("Don't have an account? Register")
        }
    }
}

