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
//import the dino, cacti, etc

object MainApp extends JFXApp {
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  stage = new PrimaryStage {
    resizable = false
    title = "Chrome Dinosaur Game"
    scene = new Scene{
      root = roots
    }
  }

  def showHomePage(): Unit ={
    val resource = getClass.getResource("view/HomePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showHowToPlay(): Unit = {
    val resource = getClass.getResource("view/HowToPlay.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }


  def showRequestPlayerName(): Unit = {
    val resource = getClass.getResource("view/RequestPlayerName.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showGamePage(): Unit = {
    val resource = getClass.getResource("view/GamePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  showHomePage()
}
