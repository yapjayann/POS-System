package assignment.POS.model
import scalafx.collections.ObservableBuffer
import assignment.POS.model.{ClothingItem, CartItem}


class ShoppingCart {

  // Observable buffer to track items in the cart
  val items: ObservableBuffer[CartItem] = new ObservableBuffer[CartItem]()

  // Method to add an item to the cart
  def addItem(item: Sellable): Unit = {
    // Check if the item is already in the cart
    val existingItemIndex = items.indexWhere(_.item == item)

    if (existingItemIndex >= 0) {
      // If the item is already in the cart, update its quantity
      val existingItem = items(existingItemIndex)
      val updatedItem = existingItem.updateQuantity(existingItem.quantity + 1)
      items.update(existingItemIndex, updatedItem)
    } else {
      // If the item is not in the cart, add it with a quantity of 1
      items += CartItem(item, 1)
    }
  }

  // Method to remove an item from the cart
  def removeItem(item: Sellable): Unit = {
    // Check if the item is in the cart
    val existingItemIndex = items.indexWhere(_.item == item)

    if (existingItemIndex >= 0) {
      val existingItem = items(existingItemIndex)
      if (existingItem.quantity > 1) {
        // If the item quantity is greater than 1, reduce the quantity
        val updatedItem = existingItem.updateQuantity(existingItem.quantity - 1)
        items.update(existingItemIndex, updatedItem)
      } else {
        // If the item quantity is 1, remove it from the cart
        items.remove(existingItemIndex)
      }
    }
  }

  // Method to print the contents of the cart (for debugging purposes)
  def printCart(): Unit = {
    println("Shopping Cart:")
    items.foreach { cartItem =>
      println(s"${cartItem.item.name} - Quantity: ${cartItem.quantity}, Total Price: ${cartItem.totalPrice}")
    }
  }

  // Method to clear the cart
  def clearCart(): Unit = {
    items.clear()
  }
}








