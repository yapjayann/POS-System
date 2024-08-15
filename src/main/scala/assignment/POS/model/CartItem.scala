package assignment.POS.model

case class CartItem(item: Sellable, quantity: Int) {
  def updateQuantity(newQuantity: Int): CartItem = {
    this.copy(quantity = newQuantity)
  }

  def totalPrice: Double = item.price * quantity
}




