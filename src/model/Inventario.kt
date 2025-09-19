package model

data class Producto(
    val nombre: String,
    val precio: Double,
    var stock: Int,
    val categoria: String
)


class Inventario {

    private val productos = listOf(
        Producto("Laptop Pro 15\"", 1200.0, 10, "Computadoras"),
        Producto("Teléfono X", 800.0, 20, "Smartphones"),
        Producto("Televisión 55\"", 950.0, 8, "Pantallas")
    )
    fun obtenerProductos(): List<Producto>{
        return productos;
    }

    fun mostrarProductos() {
        println("")
        println("=== model.Inventario ===")
        productos.forEach {
            println("${it.nombre} | Precio: $${it.precio} | Stock: ${it.stock} | Categoría: ${it.categoria}")
        }
    }
}
