package model
fun main() {
    val productos = Inventario();
    val carrito = Carrito()

    while (true) {
        println("""
            ==== MENÚ ====
            1. Mostrar productos
            2. Agregar producto al carrito
            3. Eliminar producto del carrito
            4. Ver carrito
            5. Finalizar compra
            6. Salir
        """.trimIndent())

        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("Productos disponibles:")
                //productos.forEach { println("- ${it.nombre} | Precio: ${it.precio} | Stock: ${it.cantidadDisponible}") }
                productos.mostrarProductos();
            }
            2 -> {
                println("Ingrese el nombre del producto:")
                val nombre = readLine() ?: ""
                val listaproductos = productos.obtenerProductos();
                val producto = listaproductos.find { it.nombre.equals(nombre, ignoreCase = true) }
                if (producto != null) {
                    println("Ingrese cantidad:")
                    val cantidad = readLine()?.toIntOrNull() ?: 0
                    carrito.agregarProducto(producto, cantidad)
                } else println("model.Producto no encontrado.")
            }
            3 -> {
                println("Ingrese el nombre del producto a eliminar:")
                val nombre = readLine() ?: ""
                println("Ingrese la cantidad a eliminar:")
                val cantidad = readLine()?.toIntOrNull() ?: 0
                carrito.eliminarProducto(nombre, cantidad)
            }
            4 -> carrito.mostrarCarrito()
            5 -> {
                println("Ingresar la Lógica de la factura")
            }
            6 -> {
                println(" Gracias por usar la tienda.")
                break
            }
            else -> println(" Opción inválida.")
        }
    }
}
