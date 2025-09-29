package org.example

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/**
 * Funcion principal que se encarga de mostrar el menu principal
 */
fun main() {

    val inventoryManager = InventoryManager()
    val shoppingCart = ShoppingCart(inventoryManager)
    var selectedOption: Int

    do {
        println(ConsoleHelpers.MENU_PRINCIPAL)
        selectedOption = ConsoleHelpers.askForInteger("Seleccione Opción:")
        var resultOperation : Boolean

        when (selectedOption) {
            1 -> {

                ConsoleHelpers.clearConsole()
                inventoryManager.displayInventory()

                do {
                    val productId = ConsoleHelpers.askForInteger("Ingrese el Id del producto que quiere agregar:")
                    val quantity = ConsoleHelpers.askForInteger("Ingrese la cantidad a agregar:")
                    resultOperation = shoppingCart.addUpdateProduct(productId, quantity)
                }while (resultOperation)

            }

            2 -> {

                shoppingCart.display()

                do {
                    val productId = ConsoleHelpers.askForInteger("Ingrese el Id del producto que quiere eliminar:")
                    val quantity = ConsoleHelpers.askForInteger("Ingrese la cantidad a eliminar del producto:")
                    resultOperation = shoppingCart.deleteProduct(productId, quantity)
                }while (resultOperation)

            }

            3-> {

                if (shoppingCart.isEmpty()){
                    println(ConsoleHelpers.getMessageError("Carrito actualmente esta vacío."))
                } else {
                    shoppingCart.displayWithTaxes()
                }

            }

            4 -> {

                if (shoppingCart.isEmpty()){
                    println(ConsoleHelpers.getMessageError("Carrito actualmente esta vacío."))
                }else {

                    print("${ConsoleHelpers.RESET}¿Desea finalizar la compra, una vez finalizada no se podra revertir la operación.\nEscriba 'si', si quiere continuar con la finalizacion?")

                    val answer = readlnOrNull()

                    if (answer != null &&  ( answer.lowercase() == "si" || answer.lowercase() == "sí" || answer.lowercase() == "s" ) ) {
                        shoppingCart.generatePurchasePdf()
                    }else{
                        println("${ConsoleHelpers.CYAN_BOLD}No se realizó la operación de finalizar")
                    }
                }

            }

            5 -> {
                if (shoppingCart.isEmpty()){
                    println("Saliendo...")
                    inventoryManager.saveInventoryToFile()
                }else{
                    print("${ConsoleHelpers.RESET}¿ Hay productos en el carrito desea realmente salir sin realizar la compra, responder si si desea salir?")
                    val answer = readlnOrNull()
                    if (answer != null &&  ( answer.lowercase() == "si" || answer.lowercase() == "sí" || answer.lowercase() == "s" ) ) {
                        shoppingCart.clearForClose()
                    }else{ //se coloca otra opcion para que le mande al menu
                        selectedOption = -5
                    }
                }
            }
        }
    } while (selectedOption != 5)

}