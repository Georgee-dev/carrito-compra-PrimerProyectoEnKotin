package model


data class ListaProdcuto(
    val nombre: String,
    val precio: Double,
    val stock: Int,
    val categoria: String
)


class Inventario {

    val productos = listOf(
        ListaProdcuto("Laptop Pro 15\"", 1200.0, 10, "Computadoras"),
        ListaProdcuto("Teléfono X", 800.0, 20, "Smartphones"),
        ListaProdcuto("Televisión 55\"", 950.0, 8, "Pantallas")
    )

    fun mostrarProductos() {
        println("")
        println("=== Inventario ===")
        productos.forEach {
            println("${it.nombre} | Precio: $${it.precio} | Stock: ${it.stock} | Categoría: ${it.categoria}")
        }
    }
}
