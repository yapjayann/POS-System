package assignment.POS.model


// Parametric polymorphism - CartItem can work with any type as long as T extends Sellable
case class CartItem[T <: Sellable](item: T, quantity: Int) {
  def updateQuantity(newQuantity: Int): CartItem[T] = {
    this.copy(quantity = newQuantity)
  }

  def totalPrice: Double = item.price * quantity
}




