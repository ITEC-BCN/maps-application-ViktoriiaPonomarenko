package com.example.mapsapp.ui.navigation

import android.R.attr.id
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.graphics.vector.ImageVector

enum class DrawerItem (
    val icon: ImageVector,
    val text: String,
    val route: Destination.Map
) {
    MAPA(Icons.Default.LocationOn, "Mapa", Destination.Map),
   // LIST(Icons.Default.List, "Lista", Destination.List),
   // MARKERCREATION(Icons.Default.Add, "Crear marker", Destination.List),
  //  MARKERDATAIL(Icons.Default.MoreVert, "Detalles de marker", Destination.MarkerDatails(id))

}

