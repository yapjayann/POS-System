package ch.makery.address.model

import ch.makery.address.util.{ClothesDatabase, Database}

abstract class ClothingItem(val id: String, val name: String, val price: Double) extends Sellable {
  var quantity: Int = 0

  def save(): Unit

  def updateStock(newQuantity: Int): Unit = {
    this.quantity = newQuantity
  }
}


case class Dress(override val id: String, override val name: String, override val price: Double, size: Char)
  extends ClothingItem(id, name, price) {

  override def save(): Unit = {
    Database.insertDress(this)
  }
}

case class Accessory(override val id: String, override val name: String, override val price: Double, material: String)
  extends ClothingItem(id, name, price) {

  override def save(): Unit = {
    Database.insertAccessory(this)
  }
}
