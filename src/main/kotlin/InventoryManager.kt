package org.example

import java.io.File
import java.io.InputStream

/**
 * Clase encargada de llevar el inventario del sistema de compra
 * se carga por medio del resource ubicado en el proyecto
 * archivo formula1.txt
 * De igual forma tomar en cuenta que en tiempo de ejecucion se obtiene de la carperta target
 */
class InventoryManager(){

    /**
     * Se guarda el id como indice y el producto
     */
    private val inventory = mutableMapOf<Int, Product>()

    /***
     * Ubicacion del recurso
     */
    private val resourceFormula1Path = "/formula1.txt"

    init {
        loadInventoryToFile()
    }

    /**
     * funcion privada para realizar la carga de la informacion del archivo
     */
    private fun loadInventoryToFile() {

        val inputStream: InputStream? = this::class.java.getResourceAsStream(resourceFormula1Path)

        if (inputStream == null) {
            println("El archivo de inventario no se encontró en resources.")
            return
        }

        inputStream.bufferedReader().useLines{ lines ->
            lines.forEach { line ->
                val splitResult = line.split("|")
                if (splitResult.size == 4) {
                    val id = splitResult[0].toInt()
                    val name = splitResult[1]
                    val price = splitResult[2].toDouble()
                    val quantityAvailable  = splitResult[3].toInt()
                    inventory[id] = Product(id, name, price, quantityAvailable)
                }
            }
        }

    }

    /**
     * funcion para guardar el mapa por cualquier compra que se realizo
     */
    fun saveInventoryToFile() {
        val fileUrl = this::class.java.getResource(resourceFormula1Path)
        if (fileUrl != null) {
            val file = File(fileUrl.toURI()) // Convertimos la URL en un archivo real
            file.printWriter().use { writer ->
                inventory.values.forEach {
                    writer.println("${it.id}|${it.name}|${it.price}|${it.quantityAvailable}")
                }
            }
            println("Inventario actualizado en el archivo.")
        } else {
            println("No se puede escribir en el archivo dentro de resources.")
        }
    }

    /***
     * devuelve un producto si existe en el mapa
     */
    fun getProductById(id: Int): Product? = inventory[id]

    /**
     * actualiza la cantidad en el mapa, si se envia un numero entero positivo lo disminuye y si se envia un numero negativo lo incrementa
     */
    fun updateProduct(id: Int, cantidad: Int) {
        this.inventory[id]?.let{
            it.quantityAvailable = it.quantityAvailable.minus(cantidad)
        }
    }

    /**
     * Muestra la informacion del producto en consola
     */
    fun displayInventory() {
        println("\n${ConsoleHelpers.GREEN_BOLD}Inventario disponible:")
        this.inventory.values.forEach {
            println("ID: ${it.id}, Nombre: ${it.name}, Precio: $${it.price}, Cantidad: ${it.quantityAvailable}")
        }
    }

}