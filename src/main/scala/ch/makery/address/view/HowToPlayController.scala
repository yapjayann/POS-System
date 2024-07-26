package ch.makery.address.view
import ch.makery.address.MainApp
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml
import scalafx.scene.control.Button
import scalafx.stage.Stage

@sfxml
class HowToPlayController (private val backButton: Button) {

  // Define the method to handle the button click event
  def getHome(event: ActionEvent): Unit = {
    MainApp.showHomePage()
  }
}
