package com.example.mapsapp.ui.screens

import android.widget.Toast
import androidx.compose.material3.Text
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.viewmodels.AuthViewModel

fun RegistrScreen() {
    if(authState == AuthState.Authenticated){
        navigateToHome()
    }
    else{
        if (errorMessage!!.contains("weak_password")) {
            Toast.makeText(context, "Password should be at least 6 characters", Toast.LENGTH_LONG).show()
        }

//Construim Interfície d'Usuari
        Text("ya")

    }
}