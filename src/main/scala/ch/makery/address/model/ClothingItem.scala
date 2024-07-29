package ch.makery.address.model

import ch.makery.address.util.{ClothesDatabase, Database}

abstract class ClothingItem(val id: String) extends Sellable {
  var name: String = _
  var quantity: Int = _
  var price: Double = _

  def save(): Unit
  def updateStock(newQuantity: Int): Unit = {
    this.quantity = newQuantity
  }
}

class Dress(_id: String) extends ClothingItem(_id) {
  var size: Char = _

  override def save(): Unit = {
    Database.insertDress(this)
  }
}

class Accessory(_id: String) extends ClothingItem(_id) {
  var material: String = _

  override def save(): Unit = {
    Database.insertAccessory(this)
  }
}
