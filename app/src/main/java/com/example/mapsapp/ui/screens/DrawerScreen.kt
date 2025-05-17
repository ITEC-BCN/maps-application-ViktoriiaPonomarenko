//package com.example.mapsapp.ui.screens
//
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Menu
//import androidx.compose.material3.DrawerValue
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.ModalDrawerSheet
//import androidx.compose.material3.ModalNavigationDrawer
//import androidx.compose.material3.NavigationDrawerItem
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.rememberDrawerState
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.navigation.compose.rememberNavController
//import com.example.mapsapp.ui.navigation.DrawerItem
//import com.example.mapsapp.ui.navigation.InternalNavigationWrapper
////import com.example.mapsapp.ui.navigation.InternalNavigationWrapper
//import kotlinx.coroutines.launch
//
//
//@RequiresApi(Build.VERSION_CODES.O)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DrawerScreen(function: () -> Unit) {
//    val navController = rememberNavController()
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    var selectedItemIndex by remember { mutableStateOf(0) }
//
//    ModalNavigationDrawer(
//        drawerContent = {
//            ModalDrawerSheet {
//                DrawerItem.entries.forEachIndexed { index, drawerItem ->
//                    NavigationDrawerItem(
//                        icon = {Icon(imageVector = drawerItem.icon, contentDescription = drawerItem.text)},
//                        label = { Text(text = drawerItem.text) },
//                        selected = index == selectedItemIndex,
//                        onClick = {
//                            selectedItemIndex = index
//                            scope.launch { drawerState.close() }
//                            navController.navigate(drawerItem.route)
//                        }
//                    )
//                }
//            }
//        },
//        drawerState = drawerState,
//        gesturesEnabled = false
//    ){
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("") },
//                    navigationIcon = {
//                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
//                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
//                        }
//                    }
//                )
//            }
//        )
//        { innerPadding ->
//            InternalNavigationWrapper(navController = navController, Modifier.padding(innerPadding))
//        }
//
//    }
//
//}


package com.example.mapsapp.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.mapsapp.ui.navigation.DrawerItem
import com.example.mapsapp.ui.navigation.InternalNavigationWrapper
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModel
import com.example.mapsapp.viewmodels.AuthViewModelFactory
import kotlinx.coroutines.launch
//
//@RequiresApi(Build.VERSION_CODES.O)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DrawerScreen(onLogout: () -> Unit = {}) {
//    val navController = rememberNavController()
//    val drawerState = rememberDrawerState(DrawerValue.Closed)
//    val scope = rememberCoroutineScope()
//    var selectedItemIndex by remember { mutableStateOf(0) }
//    val context = LocalContext.current
//    val authviewModel: AuthViewModel = viewModel(
//        factory = AuthViewModelFactory(SharedPreferencesHelper(context))
//    )
//
//    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
//    val drawerWidth = screenWidth * 2 / 3
//
//    ModalNavigationDrawer(
//        drawerState = drawerState,
//        gesturesEnabled = false,
//        drawerContent = {
//            ModalDrawerSheet(modifier = Modifier.width(drawerWidth)) {
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp)
//                ) {
//                    // Крестик для закрытия
//                    IconButton(
//                        onClick = { scope.launch { drawerState.close() } },
//                        modifier = Modifier.align(Alignment.Start)
//                    ) {
//                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close Menu")
//                    }
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Элементы меню
//                    DrawerItem.entries.forEachIndexed { index, drawerItem ->
//                        NavigationDrawerItem(
//                            icon = {
//                                Icon(
//                                    imageVector = drawerItem.icon,
//                                    contentDescription = drawerItem.text
//                                )
//                            },
//                            label = { Text(text = drawerItem.text) },
//                            selected = index == selectedItemIndex,
//                            onClick = {
//                                selectedItemIndex = index
//                                scope.launch { drawerState.close() }
//                                navController.navigate(drawerItem.route)
//                            }
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.weight(1f)) // Отталкивает кнопку вниз
//
//                    // Кнопка Logout
//                    Button(
//                        onClick = {
//                            authviewModel.logout()
//                            onLogout()
//                        },
//                        modifier = Modifier.fillMaxWidth()
//                    ) {
//                        Text("Logout")
//                    }
//                }
//            }
//        }
//    ) {
//        Scaffold(
//            topBar = {
//                TopAppBar(
//                    title = { Text("") },
//                    navigationIcon = {
//                        IconButton(
//                            onClick = {
//                                scope.launch { drawerState.open() }
//                            }
//                        ) {
//                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Menu")
//                        }
//                    }
//                )
//            }
//        ) { innerPadding ->
//            InternalNavigationWrapper(
//                navController = navController,
//                Modifier.padding(innerPadding)
//            )
//        }
//    }
//}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScreen(function: () -> Unit) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by remember { mutableStateOf(0) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val drawerWidth = screenWidth * 2 / 3

    // Список изображений
    val avatarUrls = listOf(
        "https://randomuser.me/api/portraits/men/1.jpg",
        "https://randomuser.me/api/portraits/women/2.jpg",
        "https://randomuser.me/api/portraits/men/3.jpg"
    )

    val randomAvatarUrl = remember { avatarUrls.random() }
    val userEmail = "user@example.com" // ← Заменить на email пользователя

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(drawerWidth)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Крестик
                    IconButton(
                        onClick = { scope.launch { drawerState.close() } },
                        modifier = Modifier.align(Alignment.Start)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Закрыть")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Круглая аватарка
                    AsyncImage(
                        model = randomAvatarUrl,
                        contentDescription = "User Avatar",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .align(Alignment.CenterHorizontally)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Email
                    Text(
                        text = userEmail,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Меню итемы
                    DrawerItem.entries.forEachIndexed { index, drawerItem ->
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = drawerItem.icon,
                                    contentDescription = drawerItem.text
                                )
                            },
                            label = { Text(text = drawerItem.text) },
                            selected = index == selectedItemIndex,
                            onClick = {
                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                    navController.navigate(drawerItem.route)
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Кнопка Logout
                    Button(
                        onClick = {  },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Logout")
                    }
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = true
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            InternalNavigationWrapper(
                navController = navController,
                Modifier.padding(innerPadding)
            )
        }
    }
}

