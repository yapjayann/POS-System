package ch.makery.address.model

case class CartItem(item: Sellable, quantity: Int, size: Option[String] = None)

