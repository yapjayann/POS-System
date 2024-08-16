package assignment.POS.model

abstract class ClothingItem(val id: String, val name: String, val price: Double, val imagePath: String) extends Sellable {
  def save(): Unit
}

case class Dress(override val id: String, override val name: String, override val price: Double, override val imagePath: String, size: String)
  extends ClothingItem(id, name, price, imagePath) {

  override def save(): Unit = {
  }
}

case class Accessory(override val id: String, override val name: String, override val price: Double, override val imagePath: String, material: String)
  extends ClothingItem(id, name, price, imagePath) {

  override def save(): Unit = {
  }
}






