package ch.makery.address.model

class ShoppingCart {
  private var items: Map[String, CartItem] = Map()

  def addItem(item: Sellable, quantity: Int = 1, size: Option[String] = None): Unit = {

  }

  def removeItem(item: Sellable, quantity: Int = 1, size: Option[String] = None): Unit = {

  }

  def getItems: Map[String, CartItem] = items

}
