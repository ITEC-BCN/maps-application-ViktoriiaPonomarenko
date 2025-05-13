package com.example.mapsapp.ui.screens

import android.widget.Toast
import androidx.compose.material3.Text
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.viewmodels.AuthViewModel

fun LoginScreen() {
    if(authState == AuthState.Authenticated){
        navigateToHome()
    }
    else{
        if (showError) {
            val errorMessage = (authState as AuthState.Error).message
            if (errorMessage!!.contains("invalid_credentials")) {
                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "An error has ocurred", Toast.LENGTH_LONG).show()
            }
            AuthViewModel.errorMessageShowed()
        }
//Construim Interfície d'Usuari
        Text("ya")

    }

}