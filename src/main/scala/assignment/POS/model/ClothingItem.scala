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

// Model to store clothes data
object ClothingItemModel {
  // ObservableBuffers for clothing items
  val dressItems = new ObservableBuffer[Dress]()
  val accessoryItems = new ObservableBuffer[Accessory]()

  // Example items
  val catDress = Dress(id = "D11111", name = "Cat Dress", price = 29.99, imagePath = "/img/catdress.png", size = "M")
  val fancyKimono = Dress(id = "D11112", name = "Fancy Kimono", price = 49.99, imagePath = "/img/fancykimono.png", size = "L")
  val magicalDress = Dress(id = "D11113", name = "Magical Dress", price = 59.99, imagePath = "/img/magicaldress.png", size = "S")
  val sweetDress = Dress(id = "D11114", name = "Sweet Dress", price = 39.99, imagePath = "/img/sweetdress.png", size = "XL")
  val giantRibbon = Accessory(id = "A22221", name = "Giant Ribbon", price = 9.99, imagePath = "/img/giantribbon.png", material = "Silk")
  val miniPleatherBag = Accessory(id = "A22222", name = "Mini Pleather Bag", price = 19.99, imagePath = "/img/minipleatherbag.png", material = "Pleather")
  val heartShades = Accessory(id = "A22223", name = "Heart Shades", price = 29.99, imagePath = "/img/heartshades.png", material = "Plastic")
  val roundFrameGlasses = Accessory(id = "A22224", name = "Round-Frame Glasses", price = 29.99, imagePath = "/img/roundframeglasses.png", material = "Glass and Metal")

  // Add items to ObservableBuffers
  dressItems ++= Seq(catDress, fancyKimono, magicalDress, sweetDress)
  accessoryItems ++= Seq(giantRibbon, miniPleatherBag, heartShades, roundFrameGlasses)
}



