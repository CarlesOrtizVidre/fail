package com.example.tareas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tareas.ui.theme.TareasTheme
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TareasTheme {
                TreballsApp()            }
        }
    }
}
data class Treballs(val id: Int, val descripcion: String, var completada: Boolean = false)

val listaTareas = List(20) { Treballs(it, "Tarea $it") }.toMutableStateList()

@Composable
fun LlistadeTreballs (Treballs: List<Treballs>, onTareaCompletada: (Treballs) -> Unit, onTreballsBorrats: (Treballs) -> Unit) {
    LazyColumn {
        items(Treballs) { tarea ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = tarea.completada,
                    onCheckedChange = { isChecked ->
                        tarea.completada = isChecked
                        onTareaCompletada(tarea)
                    }
                )
                Text(
                    text = tarea.descripcion,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    style = androidx.compose.ui.text.TextStyle(fontSize = 16.sp)
                )
                IconButton(
                    onClick = { onTreballsBorrats(tarea) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Borrar")
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TreballsApp() {
    val tareas by remember { mutableStateOf(listaTareas) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de Tareas") }
            )
        },
        content = {
            LlistadeTreballs(
                Treballs = tareas,
                onTareaCompletada = { tarea ->

                    tarea.completada = !tarea.completada
                },
                onTreballsBorrats = { tarea ->

                    tareas.remove(tarea)
                }
            )//hecho por vicente
        }
    )
}

