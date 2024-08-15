package assignment.POS

import assignment.POS.model.{Accessory, ClothingItem, Dress, CartItem,ShoppingCart}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, FXMLView, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.scene.paint.Color
import scalafx.scene.image.Image
import scalafx.scene.image.ImageView


object MainApp extends JFXApp {

  // Initialize ShoppingCart
  val shoppingCart = new ShoppingCart()

  // Example items
  val catDress = Dress(id = "1", name = "Cat Dress", price = 29.99, imagePath = "/img/catdress.png", size = 'M')
  val fancyKimono = Dress(id = "2", name = "Fancy Kimono", price = 49.99, imagePath = "/img/fancykimono.png", size = 'L')
  val giantRibbon = Accessory(id = "3", name = "Giant Ribbon", price = 9.99, imagePath = "/img/giantribbon.png", material = "Silk")
  val miniLeatherBag = Accessory(id = "4", name = "Mini Leather Bag", price = 19.99, imagePath = "/img/minipleatherbag.png", material = "Leather")

  // Add items to cart and print cart contents
  def addItemsToCart(): Unit = {
    shoppingCart.addItem(catDress)
    shoppingCart.addItem(fancyKimono)
    shoppingCart.addItem(giantRibbon)
    shoppingCart.addItem(miniLeatherBag)
    shoppingCart.addItem(catDress) // Test quantity update

    shoppingCart.printCart() // Print the cart contents to the console
  }

  // Remove items from cart and print cart contents
  def removeItemsFromCart(): Unit = {
    shoppingCart.removeItem(catDress) // Remove one Cat Dress
    shoppingCart.removeItem(miniLeatherBag) // Remove Mini Leather Bag

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

  def showMainPage(): Unit ={
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
