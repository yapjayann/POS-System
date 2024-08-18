package assignment.POS

import assignment.POS.model.ShoppingCart
import assignment.POS.util.CartDatabase
import assignment.POS.view.{CalculateSizeDialogController, CheckoutPageDialogController, EditQuantityDialogController}
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
  // Initialize the database and load cart items
  setupDB()
  ShoppingCart.instance.items.clear()
  ShoppingCart.instance.items ++= loadCartItems()

  // Background music
  private val soundtrack = new Media(getClass.getResource("/sound/Able Sisters (Sabel & Mable) - Animal Crossing New Leaf.mp3").toString)
  private val player = new MediaPlayer(soundtrack)
  // Set the MediaPlayer to loop the soundtrack indefinitely
  player.setCycleCount(MediaPlayer.Indefinite)
  player.play()
  val rootResource = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load();
  val roots = loader.getRoot[jfxs.layout.BorderPane]

  // Configure the primary stage (main window) of the application
  stage = new PrimaryStage {
    resizable = false
    title = "Animal Crossing-Themed Clothing Store POS System"
    icons += new Image(getClass.getResourceAsStream("/img/leafappicon.png")) // Set the window icon
    scene = new Scene{
      root = roots // Set the root layout as the main scene
    }
  }
  // Save cart items when closing the app
  stage.onCloseRequest = event => {
    saveCartItems(ShoppingCart.instance.items) // Save current cart items to the database
  }
  def showWelcomePage(): Unit = {
    val resource = getClass.getResource("view/WelcomePage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots) // Set the Welcome Page as the center content of the RootLayout
  }


  // Show Main (Shopping) Page
  def showMainPage(): Unit = {
    val resource = getClass.getResource("view/MainPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots) // Set the Main Page as the center content of the RootLayout
  }

  def showCartPage(): Unit = {
    val resource = getClass.getResource("view/CartPage.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load();
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showCalculateSizeDialog(): Unit = {
    val resource = getClass.getResource("view/CalculateSizeDialog.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots2 = loader.getRoot[jfxs.layout.AnchorPane]
    val control = loader.getController[CalculateSizeDialogController#Controller]

    val dialog = new Stage() {
      resizable = false
      initModality(Modality.ApplicationModal) // Make the dialog modal (blocks interaction with the main window)
      initOwner(stage)
      title = "Calculate Size"
      scene = new Scene {
        root = roots2
      }
    }

    control.dialogStage = dialog // Pass the dialog stage to the controller
    dialog.showAndWait() // Show the dialog and wait for it to be closed
  }

  // Show the Checkout Page dialog by loading the corresponding FXML file and passing the total amount to the controller
  def showCheckoutPageDialog(totalAmount: Double): Boolean = {
    val resource = getClass.getResource("view/CheckoutPageDialog.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots2 = loader.getRoot[jfxs.layout.AnchorPane]
    val control = loader.getController[CheckoutPageDialogController#Controller]

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
    control.setTotalAmount(totalAmount) // Pass the total amount to the controller
    dialog.showAndWait()

    control.checkoutSuccessful // Return whether the checkout was successful
  }

  // Show the Edit Quantity dialog by loading the corresponding FXML file and passing the current quantity and callback function to the controller
  def showEditQuantityDialog(currentQuantity: Int, callback: Int => Unit): Unit = {
    val resource = getClass.getResource("view/EditQuantityDialog.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots2 = loader.getRoot[jfxs.layout.AnchorPane]
    val control = loader.getController[EditQuantityDialogController#Controller]

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
    control.resultCallback = callback // Pass the callback function to handle the result
    control.setInitialQuantity(currentQuantity) // Set the initial quantity in the dialog
    dialog.showAndWait()
  }

  // Start the application by showing the Welcome Page
  showWelcomePage()
}
