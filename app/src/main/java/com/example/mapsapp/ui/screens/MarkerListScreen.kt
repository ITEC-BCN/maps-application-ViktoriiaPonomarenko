package com.example.mapsapp.ui.screens



//@Composable
//fun MarkerListScreen() {
//    Text("list marker")
//}



import android.R.attr.id
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mapsapp.data.Marcador
import com.example.mapsapp.viewmodels.SupaBaseViewModel


@Composable
fun MarkerListScreen(navigateToDetail: (String) -> Unit) {
    val myViewModel = viewModel< SupaBaseViewModel>()
    val marcadorList by myViewModel.marcadorList.observeAsState(emptyList<Marcador>())
    myViewModel.getAllMarcadores()
    val marcadorTitulo: String by myViewModel.marcadorTitulo.observeAsState("")
//    Column(
//        Modifier.fillMaxSize()
//    ) {
//        Column(
//            Modifier
//                .fillMaxWidth()
//                .weight(0.4f),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text("Create new student", fontSize = 28.sp, fontWeight = FontWeight.Bold)
//            TextField(value = studentName, onValueChange = { myViewModel.editMarcadorTitulo(it) })
//            TextField(value = studentMark, onValueChange = { myViewModel.editStudentMark(it) })
//            Button(onClick = { myViewModel.insertNewStudent(studentName, studentMark) }) {
//                Text("Insert")
//            }
//        }
        Text(
            "Students List",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        LazyColumn(
            Modifier
                .fillMaxWidth()
             //   .weight(0.6f)
        ) {
            items(marcadorList) { marcador ->
                val dissmissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            myViewModel.deleteMarcador(marcador.id.toString())
                            true
                        } else {
                            false
                        }
                    }
                )
                SwipeToDismissBox(state = dissmissState, backgroundContent = {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                    }
                }) {
                    MarcadorItem(marcador) { navigateToDetail(marcador.id.toString()) }
                }
            }
        }
    }


@Composable
fun MarcadorItem(marcador: Marcador, navigateToDetail: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth().background(Color.LightGray).border(width = 2.dp, Color.DarkGray)
            .clickable { navigateToDetail(marcador.id.toString()) }) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(marcador.titulo, fontSize = 28.sp, fontWeight = FontWeight.Bold)

        }
    }
}
