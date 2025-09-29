package org.example

import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import java.awt.Desktop
import java.io.File
import java.math.RoundingMode

/**
 * Carrito de compra donde se realizan validaciones y demas en esta clase
 */
class ShoppingCart (private val inventoryManager: InventoryManager) {

    /**
     * Se guardara el id del producto y la cantidad que el usuario selecciono
     */
    private val cart = mutableMapOf<Int, Int>()

    /**
     * Metodo que actualiza el carrito de compra asi como el mapa de la informacion del inventario en memoria
     */
    fun addUpdateProduct(productId: Int, quantity: Int) : Boolean {

        val product = inventoryManager.getProductById(productId)

        if (product == null) {
            println(ConsoleHelpers.getMessageError("Producto no encontrado, con id ${productId}"))
            return true
        }

        if (product.quantityAvailable >= quantity) {
            cart[productId] = cart.getOrDefault(productId, 0) + quantity
            inventoryManager.updateProduct(productId, quantity)
            println(ConsoleHelpers.getMessageSuccess("$quantity productos de ${product.name} agregado al carrito correctamente."))
            return false
        } else {
            println(ConsoleHelpers.getMessageError("cantidad insuficiente disponible del producto, solo hay ${product.quantityAvailable}."))
            return true
        }
    }

    /**
     * Metodo que elimina productos del carrito de compra asi como el mapa de la informacion del inventario en memoria
     */
    fun deleteProduct(productId: Int, quantity: Int) : Boolean {

        if ( cart.containsKey(productId) ) {

            val quantityInCart = cart[productId]

            if (quantity == quantityInCart){

                cart.remove(productId)
                inventoryManager.updateProduct(productId, (quantity * - 1))

            } else if (quantity < quantityInCart!!){

                cart.replace(productId, quantityInCart - quantity)
                inventoryManager.updateProduct(productId, (quantity * - 1))


            }else{
                println(ConsoleHelpers.getMessageError("la cantidad seleccionada $quantity es mayor a la que existe en el carrito ${quantityInCart}."))
                return true
            }

            val product = inventoryManager.getProductById(productId)

            println(ConsoleHelpers.getMessageSuccess("Se eliminó correctamente la cantidad ( ${quantity} ) del producto ( ${product?.name} 1) seleccionado exitosamente."))

            return false

        }else{
            println(ConsoleHelpers.getMessageError("Producto no encontrado en el carrito, con id ${productId}"))
            return true
        }

    }

    /**
     * Metodo para desplegar la lista de los productos agregados en el carrito mas general de la existencia
     */
    fun display() {
        println("\n${ConsoleHelpers.YELLOW_BOLD}Carrito de Compras:")
        cart.forEach { (id, quantity) ->
            val product = inventoryManager.getProductById(id)
            if (product != null) {
                println("ID: ${product.id}, ${product.name} - Cantidad: $quantity")
            }
        }
    }

    /**
     * Metodo para mostrar los productos agregados en el carrito con los precios finales
     */
    fun displayWithTaxes() {
        println("\n${ConsoleHelpers.YELLOW_BOLD}Carrito de Compras:")
        var total = 0.0
        cart.forEach { (id, quantity) ->
            val product = inventoryManager.getProductById(id)
            if (product != null) {
                val subtotal = (product.price * quantity).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
                total += subtotal
                println("${product.name} - Cantidad: $quantity - precio unitario: ${product.price} - Subtotal: $$subtotal")
            }
        }

        println("${ConsoleHelpers.PURPLE_BOLD}Cantidad de productos a pagar: ${cart.values.sum()}")
        total = total.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
        println("Total a pagar sin impuesto: $$total")

        val taxes = (total * 0.13).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble() //13%

        println("Pago de impuesto: $$taxes")
        total += taxes
        println("Total a pagar con impuesto impuesto: $$total ${ConsoleHelpers.RESET}")
    }

    fun isEmpty() : Boolean {
        return cart.isEmpty()
    }

    /**
     * funcion que genera el pdf con el mapa que esta actualmente y luego lo limpiar para continuar mas compras
     */
    fun generatePurchasePdf () {

        val fileName = "C:\\Users\\Humberto\\Desktop\\UDB\\UDB_2025\\CICLO 02\\DSM941\\DSMPROYECTOS\\carrito-compra-PrimerProyectoEnKotin\\src\\main\\resources\\pdf\\factura.pdf"
        val file = File(fileName)

        val pdfWriter = PdfWriter(file)
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument)

        // Agregar título
        var titulo = Paragraph("Factura de Compra")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(18f)
            .setBold()
        document.add(titulo)

        titulo = Paragraph("Tienda - Formula 1 a escala")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(18f)
            .setBold()
            .setFontColor(ColorConstants.MAGENTA)
        document.add(titulo)

        document.add(Paragraph("\n")) // Espaciado
        document.add(Paragraph("\n"))

        // Crear tabla con columnas
        val table = Table(UnitValue.createPercentArray(floatArrayOf(4f, 2f, 2f, 2f)))
        table.setWidth(UnitValue.createPercentValue(100f))

        // Encabezados de la tabla
        val headers = listOf("Nombre producto", "Cantidad", "Precio unitario", "Sub total")
        headers.forEach { table.addHeaderCell(Cell().add(Paragraph(it).setBold().setTextAlignment(TextAlignment.CENTER) )) }

        var total = 0.0
        cart.forEach { (id, quantity) ->
            val product = inventoryManager.getProductById(id)
            if (product != null) {
                val subtotal = (product.price * quantity).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
                total += subtotal

                table.addCell(product.name)
                table.addCell(Cell().add(Paragraph(quantity.toString()).setTextAlignment(TextAlignment.CENTER)))
                table.addCell(Cell().add(Paragraph("$%.2f".format(product.price)).setTextAlignment(TextAlignment.RIGHT)))
                table.addCell(Cell().add(Paragraph("$%.2f".format(subtotal)).setTextAlignment(TextAlignment.RIGHT)))

            }
        }

        total = total.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()

        document.add(table)

        document.add(Paragraph("\n")) // Espaciado

        val quantityParagraph = Paragraph("Cantidad de productos a pagar: ${cart.values.sum()}")
            .setTextAlignment(TextAlignment.RIGHT)
            .setFontSize(14f)
            .setBold()
            .setFontColor(ColorConstants.BLUE)

        document.add(quantityParagraph)


        var totalParagraph = Paragraph("Total a pagar sin impuestos: $%.2f".format(total))
            .setTextAlignment(TextAlignment.RIGHT)
            .setFontSize(14f)
            .setBold()
            .setFontColor(ColorConstants.BLUE)

        document.add(totalParagraph)

        val taxes = (total * 0.13).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble() //13%

        totalParagraph = Paragraph("Total de impuestos: $%.2f".format(taxes))
            .setTextAlignment(TextAlignment.RIGHT)
            .setFontSize(14f)
            .setBold()
            .setFontColor(ColorConstants.ORANGE)

        document.add(totalParagraph)

        total += taxes

        totalParagraph = Paragraph("Total a pagar con impuestos: $%.2f".format(total))
            .setTextAlignment(TextAlignment.RIGHT)
            .setFontSize(14f)
            .setBold()
            .setFontColor(ColorConstants.ORANGE)

        document.add(totalParagraph)


        document.close()

        println("${ConsoleHelpers.YELLOW_BOLD}PDF creado exitosamente: $fileName${ConsoleHelpers.RESET}")

        cart.clear()

        openPdf(fileName)

    }

    /**
     * funcion que manda instruccion de abrir el pdf en si
     * @param filePath ruta de ubicacion con el nombre del archivo a abrir
     */
    private fun openPdf(filePath: String) {
        val file = File(filePath)

        if (!file.exists()) {
            println("El archivo PDF no existe: $filePath")
            return
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file)
            } else {
                val os = System.getProperty("os.name").lowercase()
                if (os.contains("win")) {
                    Runtime.getRuntime().exec("cmd /c start $filePath") // Windows
                } else if (os.contains("mac")) {
                    Runtime.getRuntime().exec("open $filePath") // macOS
                } else if (os.contains("nix") || os.contains("nux") || os.contains("linux")) {
                    Runtime.getRuntime().exec("xdg-open $filePath") // Linux
                } else {
                    println("No se pudo abrir el PDF automáticamente en este sistema operativo.")
                }
            }
        } catch (e: Exception) {
            println("Error al abrir el PDF: ${e.message}")
        }
    }

    fun clearForClose () {
        cart.forEach { (id, quantity) ->
            inventoryManager.updateProduct(id, (quantity * - 1))
        }
        cart.clear()
    }

}