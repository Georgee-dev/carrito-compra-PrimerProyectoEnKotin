package model

//data class model.Producto(val nombre: String, val precio: Double, var stock: Int, var categoria: String)
data class ItemCarrito(val producto: Producto, var cantidad: Int) {
    val subtotal: Double
        get() = producto.precio * cantidad
}

class Carrito {
    private val items = mutableListOf<ItemCarrito>()
    // agregamos producto al carrito
    fun agregarProducto(producto: Producto, cantidad: Int) {
        if (cantidad <= producto.stock) {
            val existente = items.find { it.producto == producto }
            if (existente != null) {
                existente.cantidad += cantidad
            } else {
                items.add(ItemCarrito(producto, cantidad))
            }
            producto.stock -= cantidad
            println("✅ ${producto.nombre} agregado al carrito.")
        } else {
            println("⚠️ No hay suficiente stock de ${producto.nombre}.")
        }
    }
    // elimina producto del carrito
    fun eliminarProducto(nombre: String, cantidad: Int) {
        val item = items.find { it.producto.nombre.equals(nombre, ignoreCase = true) }
        if (item != null) {
            if (cantidad < item.cantidad) {
                item.cantidad -= cantidad
                item.producto.stock += cantidad
                println("❌ Se eliminaron $cantidad unidades de ${item.producto.nombre}.")
            } else {
                // Si pide eliminar más o igual que lo que hay, se borra todo el ítem
                item.producto.stock += item.cantidad
                items.remove(item)
                println("❌ Se eliminó ${item.producto.nombre} completamente del carrito.")
            }
        } else {
            println("⚠️ El producto no está en el carrito.")
        }
    }
    fun mostrarCarrito() {
        if (items.isEmpty()) {
            println("🛒 El carrito está vacío.")
        } else {
            println("📋 model.Carrito de compras:")
            items.forEach {
                println("- ${it.producto.nombre} | Cantidad: ${it.cantidad} | Precio: ${it.producto.precio} | Subtotal: ${it.subtotal}")
            }
            println("💰 Total general: ${calcularTotal()}")
        }
    }

    fun calcularTotal(): Double = items.sumOf { it.subtotal }

    fun obtenerItems(): List<ItemCarrito> = items
}
