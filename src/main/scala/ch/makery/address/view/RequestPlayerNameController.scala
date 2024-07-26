package ch.makery.address.view
import ch.makery.address.MainApp
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml
import scalafx.scene.control.{Alert, Button, TextField}
import scalafx.scene.control.Alert.AlertType
import scalafx.stage.Stage

@sfxml
class RequestPlayerNameController (private val startGame: Button,
                                   private val backButton: Button,
                                   private val nameTextField : TextField){

  def getHome(event: ActionEvent): Unit = {
    MainApp.showHomePage()
  }

  def getGame(event: ActionEvent): Unit ={
    val playerName = nameTextField.text.value.trim
    if (playerName.isEmpty){
      val alert = new Alert(AlertType.Warning){
        initOwner(MainApp.stage)
        title = "Invalid Name"
        headerText = "Invalid Player Name"
        contentText = "Please enter a valid player name."
      }
      alert.showAndWait()
    } else {
      MainApp.showGamePage()
    }
  }
}
