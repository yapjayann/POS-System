package assignment.POS

import assignment.POS.model.{Accessory, CartItem, ClothingItem, Dress, ShoppingCart}
import assignment.POS.view.MainPageController
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, FXMLView, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.collections.ObservableBuffer
import scalafx.scene.paint.Color
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView


object MainApp extends JFXApp {

  // Initialize ShoppingCart
  val shoppingCart = new ShoppingCart()

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
  dressItems += catDress
  dressItems += fancyKimono
  dressItems += magicalDress
  dressItems += sweetDress
  accessoryItems += giantRibbon
  accessoryItems += miniPleatherBag
  accessoryItems += heartShades
  accessoryItems += roundFrameGlasses

  // Add items to cart and print cart contents
  def addItemsToCart(): Unit = {
    shoppingCart.addItem(catDress)
    shoppingCart.addItem(fancyKimono)
    shoppingCart.addItem(giantRibbon)
    shoppingCart.addItem(miniPleatherBag)
    shoppingCart.addItem(catDress) // Test quantity update

    shoppingCart.printCart() // Print the cart contents to the console
  }

  // Remove items from cart and print cart contents
  def removeItemsFromCart(): Unit = {
    shoppingCart.removeItem(catDress) // Remove one Cat Dress
    shoppingCart.removeItem(miniPleatherBag) // Remove Mini Leather Bag

    println("\nAfter Removal:")
    shoppingCart.printCart() // Print the cart contents to the console
  }


  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  stage = new PrimaryStage {
    resizable = false
    title = "Clothing Store POS System"
    icons += new Image(getClass.getResourceAsStream("/img/leafappicon.png"))
    scene = new Scene{
      root = roots
    }
  }

  def showWelcomePage(): Unit = {
    val resource = getClass.getResource("view/WelcomePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showMainPage(): Unit = {
    val resource = getClass.getResource("view/MainPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showCartPage(): Unit = {
    val resource = getClass.getResource("view/CartPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }


  showWelcomePage()


  // Add items and print
  addItemsToCart()
  // Remove items from cart and print
  removeItemsFromCart()
}
