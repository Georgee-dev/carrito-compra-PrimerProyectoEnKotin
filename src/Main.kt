//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import  model.Productokt
import  model.Carrito
import  model.Factura
import  model.Inventario

fun main() {

    val inventario = Inventario()

    val variableString = "*** Pantalla principal ***"

    println("")
    println(variableString)

    Productokt("Test1", 0.00, 0, "NA");
    Carrito();
    Factura();
    inventario.mostrarProductos();




}