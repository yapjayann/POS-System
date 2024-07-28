package ch.makery.address.model

trait Sellable {
  def id: String
  def name: String
  def quantity: Int
  def price: Double

  def addtoCart(): Unit = { //add cart and sellable item parameters (use parametric programming for the item)

  }

  def removeFromCart(): Unit = {

  }
}
