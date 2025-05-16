package com.example.mapsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.navigation.Destination.Drawer
import com.example.mapsapp.ui.navigation.Destination.Login
import com.example.mapsapp.ui.navigation.Destination.Permissions
import com.example.mapsapp.ui.navigation.Destination.Register
import com.example.mapsapp.ui.screens.DrawerScreen
import com.example.mapsapp.ui.screens.LoginScreen
import com.example.mapsapp.ui.screens.PermissionsScreen
import com.example.mapsapp.ui.screens.RegistrScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController, Permissions) {

        composable<Permissions> {
            PermissionsScreen {
                navController.navigate(Login)
            }
        }
        composable<Login> {
            LoginScreen({ navController.navigate(Drawer) }, { navController.navigate(Register) })
        }
        composable<Register> {
            RegistrScreen { navController.navigate(Drawer) }
        }

        composable<Drawer> {
            DrawerScreen()
        }
    }

}



