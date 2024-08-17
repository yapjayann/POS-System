package assignment.POS

import assignment.POS.model.ShoppingCart
import assignment.POS.util.CartDatabase
import assignment.POS.view.{CalculateSizeController, CheckoutPageController, EditQuantityController}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, FXMLView, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.scene.image.Image
import scalafx.stage.{Modality, Stage}
import scalafx.scene.media.{Media, MediaPlayer}



object MainApp extends JFXApp with CartDatabase {
  setupDB()
  // Load cart items
  ShoppingCart.instance.items.clear()
  ShoppingCart.instance.items ++= loadCartItems()

  private val soundtrack = new Media(getClass.getResource("/sound/Able Sisters (Sabel & Mable) - Animal Crossing New Leaf.mp3").toString)
  private val player = new MediaPlayer(soundtrack)
  // Set the MediaPlayer to loop the soundtrack indefinitely
  player.setCycleCount(MediaPlayer.Indefinite)
  player.play()
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.BorderPane]

  stage = new PrimaryStage {
    resizable = false
    title = "Animal Crossing-Themed Clothing Store POS System"
    icons += new Image(getClass.getResourceAsStream("/img/leafappicon.png"))
    scene = new Scene{
      root = roots
    }
  }
  // Save cart items when closing the app
  stage.onCloseRequest = event => {
    saveCartItems(ShoppingCart.instance.items)
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

  def showCalculateSizeDialog(): Unit = {
    val resource = getClass.getResource("view/CalculateSize.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots2 = loader.getRoot[jfxs.layout.AnchorPane]
    val control = loader.getController[CalculateSizeController#Controller]

    val dialog = new Stage() {
      resizable = false
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      title = "Calculate Size"
      scene = new Scene {
        root = roots2
      }
    }

    control.dialogStage = dialog
    dialog.showAndWait()
  }

  def showCheckoutPageDialog(totalAmount: Double): Boolean = {
    val resource = getClass.getResource("view/CheckoutPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots2 = loader.getRoot[jfxs.layout.AnchorPane]
    val control = loader.getController[CheckoutPageController#Controller]

    val dialog = new Stage() {
      resizable = false
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      title = "Checkout"
      scene = new Scene {
        root = roots2
      }
    }

    control.dialogStage = dialog
    control.setTotalAmount(totalAmount)
    dialog.showAndWait()

    control.checkoutSuccessful
  }

  def showEditQuantityDialog(currentQuantity: Int, callback: Int => Unit): Unit = {
    val resource = getClass.getResource("view/EditQuantity.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots2 = loader.getRoot[jfxs.layout.AnchorPane]
    val control = loader.getController[EditQuantityController#Controller]

    val dialog = new Stage() {
      resizable = false
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      title = "Edit Quantity"
      scene = new Scene {
        root = roots2
      }
    }

    control.dialogStage = dialog
    control.resultCallback = callback
    control.setInitialQuantity(currentQuantity)  // Use the new method here
    dialog.showAndWait()
  }

  showWelcomePage()
}
