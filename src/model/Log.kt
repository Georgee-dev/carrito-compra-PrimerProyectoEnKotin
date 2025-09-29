package model


import java.time.LocalDateTime
import java.io.File

object Log {
    private val file = File("logs.txt")

    fun guardar(mensaje: String) {
        val texto = "[${LocalDateTime.now()}] $mensaje\n"
        file.appendText(texto)
    }
}