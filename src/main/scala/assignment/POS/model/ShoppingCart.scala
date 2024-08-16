package assignment.POS.model
import scalafx.collections.ObservableBuffer
import assignment.POS.model.{ClothingItem, CartItem}


class ShoppingCart {

  // Observable buffer to track items in the cart
  val items: ObservableBuffer[CartItem] = new ObservableBuffer[CartItem]()

  // Method to add an item to the cart
  // Method to add an item to the cart
  def addItem(item: Sellable): Unit = {
    println(s"Adding item: ${item.name}, ID: ${item.id}, Type: ${item.getClass.getSimpleName}")

    // Check if the item is already in the cart
    val existingItemIndex = items.indexWhere(cartItem =>
      cartItem.item.id == item.id && cartItem.item.getClass == item.getClass)

    if (existingItemIndex >= 0) {
      println(s"Item already in cart: ${item.name}, updating quantity.")
      // If the item is already in the cart, update its quantity
      val existingItem = items(existingItemIndex)
      val updatedItem = existingItem.updateQuantity(existingItem.quantity + 1)
      items.update(existingItemIndex, updatedItem)
    } else {
      println(s"Item not in cart: ${item.name}, adding to cart.")
      // If the item is not in the cart, add it with a quantity of 1
      items += CartItem(item, 1)
    }
  }

  // Method to remove an item from the cart
  def removeItem(item: Sellable): Unit = {
    // Check if the item with the same id is in the cart
    val existingItemIndex = items.indexWhere(_.item.id == item.id)

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

object ShoppingCart {
  // Singleton instance of ShoppingCart
  val instance: ShoppingCart = new ShoppingCart()
}





