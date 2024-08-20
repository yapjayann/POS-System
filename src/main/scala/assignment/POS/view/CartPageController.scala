package assignment.POS.view
import assignment.POS.MainApp
import assignment.POS.model.{Accessory, CartItem, Dress, Sellable, ShoppingCart}
import scalafx.Includes._
import scalafx.beans.property.StringProperty
import scalafx.scene.control.{Alert, Button, ButtonType, Label, TableColumn, TableView}
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
                         private val checkOutButton: Button,
                         private val clearCartButton: Button
                        ) {

  // Initialize TableView columns with data from the cart items
  itemColumn.cellValueFactory = { cellData =>
    StringProperty(cellData.value.item.name) // Display the name of the item
  }

  quantityColumn.cellValueFactory = { cellData =>
    StringProperty(cellData.value.quantity.toString) // Convert quantity to String
  }

  // Load the items from the ShoppingCart into the TableView
  cartTable.items = ShoppingCart.instance.items


  // Function to update the total amount label
  private def updateTotalAmount(): Unit = {
    val totalAmount = ShoppingCart.instance.calculateTotalPrice
    totalAmountValue.text = f"$$${totalAmount}%.2f"
  }

  // Function to update the UI, including the total amount and item details
  private def updateUI(): Unit = {
    updateTotalAmount()
    val selectedItem = Option(cartTable.selectionModel().getSelectedItem)
    showItemDetails(selectedItem)

    // Disable the clear cart button if the cart is empty
    clearCartButton.disable = ShoppingCart.instance.items.isEmpty
    checkOutButton.disable = ShoppingCart.instance.items.isEmpty
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

      case None =>
        clothingItemImage.image = null
        itemNameValue.text = ""
        itemIDValue.text = ""
        priceValue.text = ""
        sizeValue.text = ""
        materialValue.text = ""
        quantityValue.text = ""
    }
  }

  // Handler for removing the selected item from the cart
  def handleRemoveItem(): Unit = {
    val selectedItem = Option(cartTable.selectionModel().getSelectedItem)
    selectedItem match {
      case Some(item) =>
        println(s"Removing item: ${item.item.name}")
        ShoppingCart.instance.removeItem(item.item) // Remove the item from the cart
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
        // Show an error if no item is selected to remove
        println("No item selected to remove")
        val alert = new Alert(AlertType.Error) {
          title = "Selection Error"
          headerText = "Please select an item to remove."
          contentText = "No item selected selected to remove from cart."
        }
        alert.showAndWait()
    }
  }

  // Handler for checking out
  def handleCheckout(): Unit = {
    val totalAmount = ShoppingCart.instance.calculateTotalPrice
    val checkoutSuccessful = MainApp.showCheckoutPageDialog(totalAmount) // Show the checkout dialog
    if (checkoutSuccessful) {
      // Clear the cart and update the UI if checkout was successful
      cartTable.items().clear()
      updateUI()
      val alert = new Alert(AlertType.Information) {
        title = "Payment Successful"
        headerText = "You have successfully checked out."
        contentText = "Thank you for your purchase."
      }
      alert.showAndWait()
    }
  }

  // Handler for editing the quantity of the selected item
  def handleEditQuantity(): Unit = {
    val selectedItem = Option(cartTable.selectionModel().getSelectedItem)
    selectedItem match {
      case Some(item) =>
        MainApp.showEditQuantityDialog(item.quantity, newQuantity => {
          if (newQuantity == 0) {
            // Remove the item completely if quantity is set to 0
            ShoppingCart.instance.removeItemCompletely(item.item)
          } else {
            // Update the quantity of the item
            val updatedItem = item.updateQuantity(newQuantity)
            val index = ShoppingCart.instance.items.indexOf(item)
            ShoppingCart.instance.items.update(index, updatedItem)
          }
          updateUI()
        })
      case None =>
        val alert = new Alert(AlertType.Error) {
          // Show an error if no item is selected to edit
          title = "Selection Error"
          headerText = "Please select an item to edit."
          contentText = "No item selected to edit quantity."
        }
        alert.showAndWait()
    }
  }

  // Handler for clearing the cart
  def handleClearCart(): Unit = {
    // Show a confirmation dialog
    val alert = new Alert(AlertType.Confirmation) {
      title = "Clear Cart"
      headerText = "Are you sure you want to clear the cart?"
      contentText = "This action will remove all items from your cart."
    }

    val result = alert.showAndWait()

    result match {
      case Some(ButtonType.OK) =>
        // User clicked OK, clear the cart
        ShoppingCart.instance.clearCart()
        updateUI()
        showItemDetails(None)  // Clear the item details display
      case _ =>
      // User clicked Cancel or closed the dialog, do nothing
    }
  }

  // Listener to update the UI when the items in the cart change
  ShoppingCart.instance.items.onChange { (_, changes) =>
    updateUI()
  }

  // Listener to show item details when the selection changes
  cartTable.selectionModel().selectedItem.onChange { (_, oldValue, newValue) =>
    showItemDetails(Option(newValue))
  }

  // Initialize the UI when the controller is created
  updateUI()

}
