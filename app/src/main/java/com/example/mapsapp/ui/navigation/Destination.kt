package com.example.mapsapp.ui.navigation

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

sealed class Destination {

    @Serializable
    object Permissions: Destination()

    @Serializable
    object Drawer: Destination()

    @Serializable
    object Map: Destination()

    @Serializable
    object List: Destination()

//    @Serializable
//    data class MarkerCreation(val coordenada: LatLng)

    @Serializable
    data class MarkerDatails(val id:Int)

}