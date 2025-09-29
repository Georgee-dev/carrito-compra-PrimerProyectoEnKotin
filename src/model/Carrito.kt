package model
import model.Factura
import model.Log

    class Carrito( val inventario: Inventario) {

        private val items = mutableMapOf<Int,Int>() // idProducto -> cantidad

        fun verCarrito() {
            println("\n=== Carrito ===")
            if (items.isEmpty()) {
                println("Carrito vacío")
                return
            }
            items.forEach { (id,cant) ->
                val prod = inventario.obtenerProductoPorId(id)
                if (prod!=null) {
                    val subtotal = prod.precio * cant
                    println("${prod.nombre} x$cant = $${"%.2f".format(subtotal)}")
                }
            }
        }

        fun agregarcarrito(id: Int, cantidad: Int) {
            val prod = inventario.obtenerProductoPorId(id)
            when {
                prod==null -> {
                    println("Producto no encontrado")
                    Log.guardar("Error: producto $id no encontrado")
                }
                cantidad<=0 -> {
                    println("Cantidad inválida")
                    Log.guardar("Error: cantidad inválida $cantidad")
                }
                cantidad>prod.stock -> {
                    println("Stock insuficiente, solo ${prod.stock} disponibles")
                    Log.guardar("Error: stock insuficiente para ${prod.nombre}")
                }
                else -> {
                    items[id] = (items[id]?:0) + cantidad
                    println("Agregado ${prod.nombre} x$cantidad al carrito")
                    Log.guardar("Agregado ${prod.nombre} x$cantidad al carrito")
                }
            }
        }

        fun eliminarcarrito(id: Int) {
            if (items.containsKey(id)) {
                items.remove(id)
                println("Producto eliminado del carrito")
                Log.guardar("Producto $id eliminado del carrito")
            } else {
                println("Producto no estaba en el carrito")
            }
        }

        fun facturarcarrito() {
            if (items.isEmpty()) {
                println("Carrito vacío, nada que facturar")
                return
            }
            val factura = Factura(items.toMap(), inventario)
            factura.generar()
            items.clear()
        }

        companion object
    }