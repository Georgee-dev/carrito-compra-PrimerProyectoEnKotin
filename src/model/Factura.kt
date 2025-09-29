package model

class Factura(private val items: Map<Int,Int>, private val inventario: Inventario) {

    fun generar() {
        println("\n=== FACTURA ===")
        var total=0.0
        items.forEach { (id,cant) ->
            val prod = inventario.obtenerProductoPorId(id)
            if (prod!=null) {
                val subtotal=prod.precio*cant
                println("${prod.nombre} x$cant @ $${prod.precio} = $${"%.2f".format(subtotal)}")
                inventario.actualizarStock(id,cant)
                total+=subtotal
            }
        }
        val iva=total*0.13
        println("Subtotal: $${"%.2f".format(total)}")
        println("IVA (13%): $${"%.2f".format(iva)}")
        println("TOTAL: $${"%.2f".format(total+iva)}")

        Log.guardar("Compra realizada total ${"%.2f".format(total+iva)}")
        println("¡Gracias por su compra!")
    }
}