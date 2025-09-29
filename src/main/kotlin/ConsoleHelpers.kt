package org.example

/***
 * Object que sirve para poder tener funcionalidades de consola mas facilmente
 * ya sea funciones o constantes
 */
object ConsoleHelpers {

    const val RESET: String = "\u001b[0m"

    // Regular Colors

    const val RED: String = "\u001b[0;31m"
    const val GREEN: String = "\u001b[0;32m"
    const val YELLOW: String = "\u001b[0;33m"
    const val BLUE: String = "\u001b[0;34m"
    const val PURPLE: String = "\u001b[0;35m"
    const val CYAN: String = "\u001b[0;36m"

    const val RED_BOLD: String = "\u001b[1;31m"
    const val GREEN_BOLD: String = "\u001b[1;32m"
    const val YELLOW_BOLD: String = "\u001b[1;33m"
    const val BLUE_BOLD: String = "\u001b[1;34m"
    const val PURPLE_BOLD: String = "\u001b[1;35m"
    const val CYAN_BOLD: String = "\u001b[1;36m"
    const val WHITE_BOLD: String = "\u001b[1;37m"

    val MENU_PRINCIPAL = """
        ${this.CYAN}Carrito Compra - Formula 1 a escala
        Menu principal
            1. Agregar producto al carrito
            2. Eliminar producto al carrito
            3. Mostrar Carrito actual
            4. Finalizar compra
            5. Salir
        """.trimIndent()

    fun getMessageError(message : String) : String {
        return "${this.RED_BOLD}$message${this.RESET}";
    }

    fun getMessageSuccess(message : String) : String {
        return "${this.BLUE_BOLD}$message${this.RESET}";
    }

    fun clearConsole() {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }

    /**
     * Funcion que pide un numero entero positivo hasta que sea correcto
     */
    fun askForInteger(question: String): Int {
        while (true) {
            print("${this.RESET} $question ")
            try {
                val input = readLine()!!.toInt()
                if (input >0){
                    return input;
                }else{
                    println(getMessageError("El número debe ser mayor a 0"));
                }
            } catch (e: NumberFormatException) {
                println(getMessageError("Debe Seleccionar un numero entero"));
            }
        }
    }

}