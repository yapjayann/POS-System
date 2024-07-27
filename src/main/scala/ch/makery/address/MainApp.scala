package ch.makery.address
import ch.makery.address.view.HowToPlayController
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, FXMLView, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint.Color
import scalafx.animation.AnimationTimer
import scalafx.scene.image.Image
//import the dino, cacti, etc

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


}
