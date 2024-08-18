package assignment.POS.model
import scalafx.collections.ObservableBuffer
import assignment.POS.util.CartDatabase

class ShoppingCart extends CartDatabase {

  // Observable buffer to track items in the cart
  val items: ObservableBuffer[CartItem[_ <: Sellable]] = new ObservableBuffer[CartItem[_ <: Sellable]]()

  // Method to add an item to the cart
  def addItem[T <: Sellable](item: T): Unit = {

    // Check if the item is already in the cart
    val existingItemIndex = items.indexWhere(cartItem =>
      cartItem.item.id == item.id && cartItem.item.getClass == item.getClass)

    if (existingItemIndex >= 0) {
      // Update quantity if item already in cart
      val existingItem = items(existingItemIndex)
      val updatedItem = existingItem.updateQuantity(existingItem.quantity + 1)
      items.update(existingItemIndex, updatedItem)
    } else {
      // Add item if not already in cart
      items += CartItem(item, 1)
    }
    saveCartItems(items) // Save cart items to database
  }

  // Method to remove an item from the cart
  def removeItem[T <: Sellable](item: T): Unit = {

    // Find the index of the item in the cart
    val existingItemIndex = items.indexWhere(_.item.id == item.id)

    if (existingItemIndex >= 0) {
      val existingItem = items(existingItemIndex)
      if (existingItem.quantity > 1) {
        // Decrease quantity if more than one item is in the cart
        val updatedItem = existingItem.updateQuantity(existingItem.quantity - 1)
        items.update(existingItemIndex, updatedItem)
      } else {
        // Remove item from cart if quantity is 1
        items.remove(existingItemIndex)
      }
    }
    saveCartItems(items) // Save cart items to database
  }

  // Method to remove all instances of an item from the cart
  def removeItemCompletely[T <: Sellable](item: T): Unit = {
    println(s"Removing item completely: ${item.name}, ID: ${item.id}")
    items.removeAll(items.filter(_.item.id == item.id))
    saveCartItems(items) // Save cart items to database
  }


  // Method to clear all items from the cart
  def clearCart(): Unit = {
    items.clear()
    saveCartItems(items) // Save cart items to database
  }

  // Method to calculate the total price of items in the cart
  def calculateTotalPrice: Double = {
    items.foldLeft(0.0) { (acc, cartItem) =>
      acc + (cartItem.item.price * cartItem.quantity)
    }
  }
}

object ShoppingCart {
  // Singleton instance of ShoppingCart
  val instance: ShoppingCart = new ShoppingCart()
}





