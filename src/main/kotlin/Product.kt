package org.example

/**
 * Clase Producto para el manejo de la informacion del carrito
 */
data class Product (
    var id : Int,
    var name: String,
    var price : Double,
    var quantityAvailable  : Int
);