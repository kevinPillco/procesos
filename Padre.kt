import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val tablero = Array(3) { Array(3) { '-' } }
    var jugadorActual = 'X'
    var juegoTerminado = false

    fun imprimirTablero() {
        tablero.forEach { println(it.joinToString(" | ")) }
        println()
    }

    fun verificarGanador(): Boolean {
        // Verifica si hay un ganador (filas, columnas o diagonales)
        for (i in 0..2) {
            if (tablero[i][0] == jugadorActual && tablero[i][1] == jugadorActual && tablero[i][2] == jugadorActual) return true
            if (tablero[0][i] == jugadorActual && tablero[1][i] == jugadorActual && tablero[2][i] == jugadorActual) return true
        }
        if (tablero[0][0] == jugadorActual && tablero[1][1] == jugadorActual && tablero[2][2] == jugadorActual) return true
        if (tablero[0][2] == jugadorActual && tablero[1][1] == jugadorActual && tablero[2][0] == jugadorActual) return true
        return false
    }

    fun solicitarJugada(): Pair<Int, Int> {
        println("Turno del jugador $jugadorActual. Introduce tu jugada (fila,columna):")
        val jugada = readLine()!!

        // Llamar al proceso hijo para validar la jugada
        val command = listOf(
            "/home/kevin/.jdks/openjdk-23/bin/java",
            "-javaagent:/home/kevin/.local/share/JetBrains/Toolbox/apps/intellij-idea-community-edition/lib/idea_rt.jar=35601:/home/kevin/.local/share/JetBrains/Toolbox/apps/intellij-idea-community-edition/bin",
            "-Dfile.encoding=UTF-8",
            "-Dsun.stdout.encoding=UTF-8",
            "-Dsun.stderr.encoding=UTF-8",
            "-classpath",
            "/home/kevin/Escritorio/clases/psp/ejemploArray/out/production/ejemploArray:/home/kevin/.m2/repository/org/jetbrains/kotlin/kotlin-stdlib/2.0.20/kotlin-stdlib-2.0.20.jar:/home/kevin/.m2/repository/org/jetbrains/annotations/13.0/annotations-13.0.jar",
            "HijoKt"
        )

        val proceso = ProcessBuilder(command).start()
        val out = OutputStreamWriter(proceso.outputStream)
        val leer = BufferedReader(InputStreamReader(proceso.inputStream))

        // Enviamos la jugada al proceso hijo
        out.write(jugada + "\n")
        out.flush()

        // Leemos la respuesta del hijo
        val respuesta = leer.readLine()
        val (fila, columna, esValida) = respuesta.split(",")

        if (esValida.toBoolean()) {
            return Pair(fila.toInt(), columna.toInt())
        } else {
            println("Jugada no válida, intenta de nuevo.")
            return solicitarJugada()
        }
    }

    // Juego principal
    while (!juegoTerminado) {
        imprimirTablero()

        // Solicitar jugada al jugador actual
        val (fila, columna) = solicitarJugada()

        // Actualizar el tablero
        if (tablero[fila][columna] == '-') {
            tablero[fila][columna] = jugadorActual

            // Verificar si el jugador actual ganó
            if (verificarGanador()) {
                imprimirTablero()
                println("¡El jugador $jugadorActual ha ganado!")
                juegoTerminado = true
            } else if (tablero.all { fila -> fila.all { it != '-' } }) {
                // Verificar si el tablero está lleno (empate)
                imprimirTablero()
                println("¡El juego ha terminado en empate!")
                juegoTerminado = true
            } else {
                // Cambiar de jugador
                jugadorActual = if (jugadorActual == 'X') 'O' else 'X'
            }
        } else {
            println("Esa posición ya está ocupada. Intenta de nuevo.")
        }
    }
}
