package assignment.POS.model


// Parametric polymorphism - CartItem can work with any type as long as T extends Sellable
case class CartItem[T <: Sellable](item: T, quantity: Int) {
  // Updates the quantity of the item
  def updateQuantity(newQuantity: Int): CartItem[T] = {
    this.copy(quantity = newQuantity)
  }

  // Calculates the total price for this cart item
  def totalPrice(): Double = item.price * quantity
}




