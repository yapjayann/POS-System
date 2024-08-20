package assignment.POS.model


// Abstract class representing a clothing item
abstract class ClothingItem(val id: String, val name: String, val price: Double, val imagePath: String) extends Sellable {

  // Provides a description of the clothing item (to be overridden)
  def getDescription: String = s"$name - $$${price}"
}

// Polymorphism - dress and accessory extends ClothingItem but have specific unique properties (size and material)
// Dress is a specific type of ClothingItem with an additional size property
case class Dress(override val id: String, override val name: String, override val price: Double, override val imagePath: String, size: String)
  extends ClothingItem(id, name, price, imagePath) {

  // Provides a description including the size of the dress
  override def getDescription: String = s"$name (Size: $size) - $$${price}"
}

// Accessory is a specific type of ClothingItem with an additional material property
case class Accessory(override val id: String, override val name: String, override val price: Double, override val imagePath: String, material: String)
  extends ClothingItem(id, name, price, imagePath) {

  // Provides a description including the material of the accessory
  override def getDescription: String = s"$name ($material) - $$${price}"
}





