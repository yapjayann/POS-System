package assignment.POS.view
import assignment.POS.model.{CartItem, Sellable, ShoppingCart}
import scalafx.Includes._
import scalafx.beans.property.{IntegerProperty, ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{Button, Label, TableColumn, TableView}
import scalafxml.core.macros.sfxml
import scalafx.beans.value.ObservableValue

@sfxml
class CartPageController(private val cartTable: TableView[CartItem],
                         private val itemColumn: TableColumn[CartItem, String],
                         private val quantityColumn: TableColumn[CartItem, String],
                         private val totalAmountValue: Label,
                         private val itemNameValue: Label,
                         private val itemIDValue: Label,
                         private val priceValue: Label,
                         private val sizeValue: Label,
                         private val materialValue: Label,
                         private val quantityValue: Label,
                         private val editQuantityButton: Button,
                         private val removeButton: Button,
                         private val checkOutButton: Button
                        ) {

  // Initialize TableView columns
  itemColumn.cellValueFactory = { cellData =>
    StringProperty(cellData.value.item.name) // Use StringProperty directly
  }

  quantityColumn.cellValueFactory = { cellData =>
    StringProperty(cellData.value.quantity.toString) // Convert quantity to String
  }
  // Load cart items into TableView
  cartTable.items = ShoppingCart.instance.items

  // Load cart items into TableView
  cartTable.items = ShoppingCart.instance.items

  // Function to update the total amount label
  private def updateTotalAmount(): Unit = {
    val totalAmount = ShoppingCart.instance.calculateTotalPrice
    totalAmountValue.text = f"$$${totalAmount}%.2f"
  }

  // Update total amount when the controller is initialized
  updateTotalAmount()

}
