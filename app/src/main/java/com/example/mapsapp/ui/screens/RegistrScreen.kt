package com.example.mapsapp.ui.screens

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.material3.Text
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModel
import com.example.mapsapp.viewmodels.AuthViewModelFactory
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegistrScreen(onRegisterSuccess: () -> Unit) {
    val context = LocalContext.current

    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(SharedPreferencesHelper(context))
    )

    val authState by viewModel.authState.observeAsState()
    val showError by viewModel.showError.observeAsState(false)

    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")

    if(authState == AuthState.Authenticated){
        onRegisterSuccess()
    }
    else{
        if (showError) {
            val errorMessage = (authState as AuthState.Error).message
            if (errorMessage!!.contains("invalid_credentials")) {
                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "An error has ocurred", Toast.LENGTH_LONG).show()
            }
            viewModel.errorMessageShowed()
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
        Text("Register", style = MaterialTheme.typography.headlineMedium)

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
            onClick = { viewModel.signUp() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

    }
}
