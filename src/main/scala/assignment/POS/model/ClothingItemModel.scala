package assignment.POS.model

import scalafx.collections.ObservableBuffer

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
  val balletOutfit = Dress(id = "D11115", name = "Ballet Outfit", price = 69.99, imagePath = "/img/balletoutfit.png", size = "XS")
  val helloKittyDress = Dress(id = "D11116", name = "Hello Kitty Dress", price = 39.99, imagePath = "/img/hellokittydress.png", size = "XS")
  val watermelonDress = Dress(id = "D11117", name = "Watermelon Dress", price = 29.99, imagePath = "/img/watermelondress.png", size = "M")
  val weddingDress = Dress(id = "D11118", name = "Wedding Dress", price = 499.99, imagePath = "/img/weddingdress.png", size = "S")
  val giantRibbon = Accessory(id = "A22221", name = "Giant Ribbon", price = 9.99, imagePath = "/img/giantribbon.png", material = "Silk")
  val miniPleatherBag = Accessory(id = "A22222", name = "Mini Pleather Bag", price = 19.99, imagePath = "/img/minipleatherbag.png", material = "Pleather")
  val heartShades = Accessory(id = "A22223", name = "Heart Shades", price = 29.99, imagePath = "/img/heartshades.png", material = "Plastic")
  val roundFrameGlasses = Accessory(id = "A22224", name = "Round-Frame Glasses", price = 29.99, imagePath = "/img/roundframeglasses.png", material = "Glass and Metal")
  val bunnyEars = Accessory(id = "A22225", name = "Bunny Ears", price = 19.99, imagePath = "/img/bunnyears.png", material = "PVC")
  val canvasBackpack = Accessory(id = "A22226", name = "Canvas Backpack", price = 69.99, imagePath = "/img/canvasbackpack.png", material = "High Quality Canvas")
  val elegantHat = Accessory(id = "A22227", name = "Elegant Hat", price = 49.99, imagePath = "/img/eleganthat.png", material = "Cotton")
  val simpleSunglasses = Accessory(id = "A22228", name = "Simple Sunglasses", price = 39.99, imagePath = "/img/simplesunglasses.png", material = "Plastic")


  // Add items to ObservableBuffers
  dressItems ++= Seq(catDress, fancyKimono, magicalDress, sweetDress, balletOutfit, helloKittyDress, watermelonDress, weddingDress)
  accessoryItems ++= Seq(giantRibbon, miniPleatherBag, heartShades, roundFrameGlasses, bunnyEars, canvasBackpack, elegantHat, simpleSunglasses)
}
