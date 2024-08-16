package assignment.POS

import assignment.POS.model.{Accessory, CartItem, ClothingItem, ClothingItemModel, Dress, ShoppingCart}
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
}
