package assignment.POS.model

import scala.collection.mutable.Map

class ShoppingCart {

  // Use a mutable Map to track items by their ID
  private var cartItems: Map[String, CartItem] = Map()

  // Add a Sellable item to the cart and update the internal data structure
  def addItem(item: Sellable): Unit = {
    cartItems.get(item.id) match {
      case Some(cartItem) =>
        // If item already exists, update the quantity
        val updatedItem = cartItem.updateQuantity(cartItem.quantity + 1)
        cartItems.update(item.id, updatedItem)
      case None =>
        // If item does not exist, add it to the cart
        val newItem = CartItem(item, 1)
        cartItems += (item.id -> newItem)
    }
  }

  // Remove a Sellable item from the cart and update the internal data structure
  def removeItem(item: Sellable): Unit = {
    cartItems.get(item.id) match {
      case Some(cartItem) if cartItem.quantity > 1 =>
        // If quantity > 1, decrease the quantity
        val updatedItem = cartItem.updateQuantity(cartItem.quantity - 1)
        cartItems.update(item.id, updatedItem)
      case Some(_) =>
        // If quantity is 1, remove the item from the cart
        cartItems -= item.id
      case None =>
      // Item not found in the cart
    }
  }

  // Get all items in the cart
  def getItems: List[CartItem] = {
    cartItems.values.toList
  }

  // Get total price of all items in the cart
  def getTotal: Double = {
    getItems.map(_.totalPrice).sum
  }

  // Print all items in the cart to the console
  def printCart(): Unit = {
    println("Shopping Cart Contents:")
    getItems.foreach { item =>
      println(s"Item: ${item.item.name}, Quantity: ${item.quantity}, Total Price: ${item.totalPrice}")
    }
    println(s"Total Cart Price: ${getTotal}")
  }
}







