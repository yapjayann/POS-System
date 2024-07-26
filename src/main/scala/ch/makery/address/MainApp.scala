package ch.makery.address
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}
import javafx.{scene => jfxs}

object MainApp extends JFXApp {
  // transform path of RootLayout.fxml to URI for resource location.

  val rootResource = getClass.getResource("view/HomePage.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.AnchorPane]
  stage = new PrimaryStage {
    title = "Dinosaur game"
    scene = new Scene {
      root = roots
    }
  }
}
