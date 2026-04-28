package ramirez.ruben.threadsandcorutines.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Collections
import kotlin.system.measureTimeMillis

fun ejecutarConHilos(currentLog: String): Pair<Long, String> {
    val log = Collections.synchronizedList(currentLog.split("\n").toMutableList())

    val transcurrido = measureTimeMillis {
        val threads = mutableListOf<Thread>()

        repeat(10000) { index ->
            val thread = Thread {
                Thread.sleep(200)
                log.add("**Hilo** Tarea ${index} ejecutada")
            }
            thread.start()
            threads.add(thread)
        }
        threads.forEach { it.join() }
    }

    log.add("--------Total hilos 10k, en $transcurrido")

    val finalLog = synchronized(log) {
        log.joinToString("\n")
    }

    return transcurrido to finalLog
}

suspend fun ejecutarCorrutinas(currentLog: String, scope: CoroutineScope): Pair<Long, String> {
    val log = Collections.synchronizedList(currentLog.split("\n").toMutableList())

    val transcurrido = measureTimeMillis {
        coroutineScope {
            List(10000) { index ->
                launch(Dispatchers.IO) {
                    delay(200)
                    log.add("***Corrutinas*** Tarea $index terminada")
                }
            }
        }
    }

    log.add("---TOTAL CORRUTINAS 10000 en ${transcurrido}")

    val finalLog = synchronized(log) {
        log.joinToString("\n")
    }

    return transcurrido to finalLog
}

fun getMemoryUsage(): String {
    val runtime = Runtime.getRuntime()
    val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
    return "$usedMemory MB"
}