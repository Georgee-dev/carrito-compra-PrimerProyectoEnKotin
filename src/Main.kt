import model.Carrito
import model.Factura
import model.Inventario
import model.Log

fun main() {
    val inventario = Inventario()
    val carrito = Carrito(inventario)

    var salir = false
    while (!salir) {
        println("\n=== TIENDA INFORMATICA ===")
        println("1. Mostrar productos")
        println("2. Agregar al carrito")
        println("3. Eliminar del carrito")
        println("4. Ver carrito")
        println("5. Facturar")
        println("6. Salir")
        print("Seleccione opción: ")

        when (readLine()) {
            "1" -> inventario.mostrarProductos()

            "2" -> {
                inventario.mostrarProductos()
                print("Ingrese ID del producto: ")
                val idInput = readLine()?.toIntOrNull()
                if (idInput == null) {
                    println("Entrada inválida")
                    continue
                }
                val id = idInput

                print("Ingrese cantidad: ")
                val cantidadInput = readLine()?.toIntOrNull()
                if (cantidadInput == null) {
                    println("Entrada inválida")
                    continue
                }
                val cantidad = cantidadInput

                carrito.agregarcarrito(id, cantidad)
            }

            "3" -> {
                print("Ingrese ID del producto a eliminar del carrito: ")
                val idInput = readLine()?.toIntOrNull()
                if (idInput == null) {
                    println("Entrada inválida")
                    continue
                }
                val id = idInput

                carrito.eliminarcarrito(id)
            }

            "4" -> carrito.verCarrito()

            "5" -> carrito.facturarcarrito()

            "6" -> {
                println("Adiós.")
                salir = true
            }

            else -> {
                println("Opción inválida")
            }
        }
    }
}