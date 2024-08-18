package assignment.POS.model
import scalafx.collections.ObservableBuffer
import assignment.POS.util.CartDatabase

class ShoppingCart extends CartDatabase {

  // Observable buffer to track items in the cart
  val items: ObservableBuffer[CartItem[_ <: Sellable]] = new ObservableBuffer[CartItem[_ <: Sellable]]()

  // Method to add an item to the cart
  def addItem[T <: Sellable](item: T): Unit = {
    println(s"Adding item: ${item.name}, ID: ${item.id}, Type: ${item.getClass.getSimpleName}")

    val existingItemIndex = items.indexWhere(cartItem =>
      cartItem.item.id == item.id && cartItem.item.getClass == item.getClass)

    if (existingItemIndex >= 0) {
      println(s"Item already in cart: ${item.name}, updating quantity.")
      val existingItem = items(existingItemIndex)
      val updatedItem = existingItem.updateQuantity(existingItem.quantity + 1)
      items.update(existingItemIndex, updatedItem)
    } else {
      println(s"Item not in cart: ${item.name}, adding to cart.")
      items += CartItem(item, 1)
    }
    saveCartItems(items)
  }

  // Method to remove an item from the cart
  def removeItem[T <: Sellable](item: T): Unit = {
    println(s"Attempting to remove item: ${item.name}, ID: ${item.id}")

    val existingItemIndex = items.indexWhere(_.item.id == item.id)

    if (existingItemIndex >= 0) {
      val existingItem = items(existingItemIndex)
      if (existingItem.quantity > 1) {
        val updatedItem = existingItem.updateQuantity(existingItem.quantity - 1)
        items.update(existingItemIndex, updatedItem)
        println(s"Decreased quantity for item: ${item.name}. New quantity: ${updatedItem.quantity}")
      } else {
        items.remove(existingItemIndex)
        println(s"Removed item from cart: ${item.name}")
      }
    } else {
      println(s"Item not found in cart: ${item.name}")
    }
    saveCartItems(items)
  }

  def removeItemCompletely[T <: Sellable](item: T): Unit = {
    println(s"Removing item completely: ${item.name}, ID: ${item.id}")
    items.removeAll(items.filter(_.item.id == item.id))
    saveCartItems(items)
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
    saveCartItems(items)
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





