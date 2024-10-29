import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val leer = BufferedReader(InputStreamReader(System.`in`))
    val out = OutputStreamWriter(System.out)

    // Recibe la jugada del padre (formato "fila,columna")
    val jugada = leer.readLine()

    // Split para obtener fila y columna
    val (fila, columna) = jugada.split(",")

    // Validar la jugada (por simplicidad, siempre la aceptamos en este ejemplo)
    val esValida = true

    // Devolver la jugada con validación al padre
    out.write("$fila,$columna,$esValida\n")
    out.flush()
}
