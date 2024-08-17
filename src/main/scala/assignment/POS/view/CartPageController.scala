package assignment.POS.view
import assignment.POS.MainApp
import assignment.POS.model.{Accessory, CartItem, Dress, Sellable, ShoppingCart}
import scalafx.Includes._
import scalafx.beans.property.{StringProperty}
import scalafx.scene.control.{Alert, Button, Label, TableColumn, TableView}
import scalafxml.core.macros.sfxml
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.image.{Image, ImageView}

@sfxml
class CartPageController(private val cartTable: TableView[CartItem[_ <: Sellable]],
                         private val itemColumn: TableColumn[CartItem[_ <: Sellable], String],
                         private val quantityColumn: TableColumn[CartItem[_ <: Sellable], String],
                         private val totalAmountValue: Label,
                         private val clothingItemImage: ImageView,
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


  // Function to update the total amount label
  private def updateTotalAmount(): Unit = {
    val totalAmount = ShoppingCart.instance.calculateTotalPrice
    totalAmountValue.text = f"$$${totalAmount}%.2f"
  }

  private def updateUI(): Unit = {
    updateTotalAmount()
    val selectedItem = Option(cartTable.selectionModel().getSelectedItem)
    showItemDetails(selectedItem)
  }




  // listener to react to changes in the cart
  ShoppingCart.instance.items.onChange { (_, changes) =>
    updateUI()
  }

  // Selection listener for the cart table
  cartTable.selectionModel().selectedItem.onChange { (_, oldValue, newValue) =>
    showItemDetails(Option(newValue))
  }

  // Function to show item details
  private def showItemDetails(cartItem: Option[CartItem[_ <: Sellable]]): Unit = {
    cartItem match {
      case Some(item) =>
        val clothingItem = item.item
        clothingItemImage.image = new Image(clothingItem.imagePath)
        itemNameValue.text = clothingItem.name
        itemIDValue.text = clothingItem.id
        priceValue.text = f"$$${clothingItem.price}%.2f"
        quantityValue.text = item.quantity.toString

        clothingItem match {
          case dress: Dress =>
            sizeValue.visible = true
            materialValue.visible = false
            sizeValue.text = s"${dress.size}"

          case accessory: Accessory =>
            sizeValue.visible = false
            materialValue.visible = true
            materialValue.text = s"${accessory.material}"
        }

        println(s"Item details set: ${itemNameValue.text()}, ${itemIDValue.text()}, ${priceValue.text()}, ${quantityValue.text()}") // Debug print

      case None =>
        clothingItemImage.image = null
        itemNameValue.text = ""
        itemIDValue.text = ""
        priceValue.text = ""
        sizeValue.text = ""
        materialValue.text = ""
        quantityValue.text = ""
        println("No item selected, details cleared") // Debug print
    }
  }

  def handleRemoveItem(): Unit = {
    val selectedItem = Option(cartTable.selectionModel().getSelectedItem)
    selectedItem match {
      case Some(item) =>
        println(s"Removing item: ${item.item.name}")
        ShoppingCart.instance.removeItem(item.item)
        updateUI()

        // If the cart is now empty, clear the details
        if (ShoppingCart.instance.items.isEmpty) {
          showItemDetails(None)
        } else {
          // Select the next item in the table, or the last one if we removed the last item
          val nextIndex = math.min(cartTable.selectionModel().getSelectedIndex, ShoppingCart.instance.items.size - 1)
          cartTable.selectionModel().select(nextIndex)
        }
      case None =>
        println("No item selected to remove")
        val alert = new Alert(AlertType.Error) {
          title = "Selection Error"
          headerText = "Please select an item to remove."
          contentText = "No item selected selected to remove from cart."
        }
        alert.showAndWait()
    }
  }

  def handleCheckout(): Unit = {
    val totalAmount = ShoppingCart.instance.calculateTotalPrice
    val checkoutSuccessful = MainApp.showCheckoutPageDialog(totalAmount)
    if (checkoutSuccessful) {
      // Clear the cart table and update the UI
      cartTable.items().clear()
      updateUI()
      val alert = new Alert(AlertType.Confirmation) {
        title = "Payment Successful"
        headerText = "You have successfully checked out."
        contentText = "Thank you for your purchase."
      }
      alert.showAndWait()
    }
  }

  def handleEditQuantity(): Unit = {
    val selectedItem = Option(cartTable.selectionModel().getSelectedItem)
    selectedItem match {
      case Some(item) =>
        MainApp.showEditQuantityDialog(item.quantity, newQuantity => {
          if (newQuantity == 0) {
            // Remove the item completely if quantity is set to 0
            ShoppingCart.instance.removeItemCompletely(item.item)
          } else {
            val updatedItem = item.updateQuantity(newQuantity)
            val index = ShoppingCart.instance.items.indexOf(item)
            ShoppingCart.instance.items.update(index, updatedItem)
          }
          updateUI()
        })
      case None =>
        val alert = new Alert(AlertType.Error) {
          title = "Selection Error"
          headerText = "Please select an item to edit."
          contentText = "No item selected to edit quantity."
        }
        alert.showAndWait()
    }
  }

  // Initialize the UI
  updateUI()

}
