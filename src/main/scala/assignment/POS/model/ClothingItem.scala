package assignment.POS.model

import scalafx.collections.ObservableBuffer

// Abstract class ClothingItem
abstract class ClothingItem(val id: String, val name: String, val price: Double, val imagePath: String) extends Sellable {

}

// Polymorphism - dress and accessory extends ClothingItem but have specific unique properties (size and material)
case class Dress(override val id: String, override val name: String, override val price: Double, override val imagePath: String, size: String)
  extends ClothingItem(id, name, price, imagePath) {

}

case class Accessory(override val id: String, override val name: String, override val price: Double, override val imagePath: String, material: String)
  extends ClothingItem(id, name, price, imagePath) {


}





