package com.example.mapsapp.data

import kotlinx.serialization.Serializable


@Serializable
data class Marcador(
    val id: Int? = null,
    val titulo: String,
    val descripcion: String,
    val foto: String,
    val coordenadas: Int
)
