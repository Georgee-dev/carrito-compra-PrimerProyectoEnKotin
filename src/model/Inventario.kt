package model
import main
import com.sun.xml.internal.bind.v2.model.core.ID


data class ListaProdcuto(
    val nombre: String,
    val precio: Double,
    var stock: Int,
    val categoria: String,
    val ID: Int,
)


class Inventario {

    var productos = mutableListOf(
        ListaProdcuto("Laptop Pro 15\"", 1200.0, 10, "Computadoras",1),
        ListaProdcuto("Teléfono X", 800.0, 20, "Smartphones",2),
        ListaProdcuto("Televisión 55\"", 950.0, 8, "Pantallas",3)
    )

    fun mostrarProductos() {
        println("")
        println("=== Inventario ===")
        productos.forEach {
            println("Codigo: ${it.ID}| Nombre:${it.nombre} | Precio: $${it.precio} | Stock: ${it.stock} | Categoría: ${it.categoria}")
        }
    }
    fun obtenerProductoPorId(id: Int): ListaProdcuto? = productos.find { it.ID == id }
    fun actualizarStock(id: Int, cantidad: Int) {
        productos.find { it.ID == id }?.let { it.stock -= cantidad }
    }
}
