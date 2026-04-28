package ramirez.ruben.threadsandcorutines.screens

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ramirez.ruben.threadsandcorutines.utils.ejecutarConHilos
import ramirez.ruben.threadsandcorutines.utils.ejecutarCorrutinas
import ramirez.ruben.threadsandcorutines.utils.getMemoryUsage

@Composable
fun ComparativaScreen(innerPadding: PaddingValues) {
    var statusText by remember { mutableStateOf("Press") }
    var log by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    scope.launch {
                        statusText = "Ejecutando con HILOS"
                        log = ""
                        val (tiempo, logFinal) = ejecutarConHilos(log)
                        log = logFinal
                        statusText = "Hilos terminados en ${tiempo}, ${getMemoryUsage()}"

                    }
                },
                modifier = Modifier.weight(1f)
            ) { Text("Hilos") }
            Button(
                onClick = {
                    scope.launch {
                        statusText = "Ejecutando corrutinas"
                        log = ""
                        val (tiempo, logFinal) = ejecutarCorrutinas(log, scope)
                        log = logFinal
                        statusText = "Corrutinas terminadas en ${tiempo}, ${getMemoryUsage()}"
                    }
                },
                Modifier.weight(1f)
            ) {
                Text("Corrutinas")
            }
        }
        Spacer(Modifier.height(16.dp))

        Text("Registro de eventos")
        Surface(Modifier
            .fillMaxWidth()
            .weight(1f)
            .verticalScroll(scrollState)) {
            Text(log, Modifier.padding(8.dp), fontSize = 10.sp)
        }
    }
}