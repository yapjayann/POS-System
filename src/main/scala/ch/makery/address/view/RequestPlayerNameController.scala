package ch.makery.address.view
import ch.makery.address.MainApp
import scalafx.Includes._
import scalafx.event.ActionEvent
import scalafxml.core.macros.sfxml
import scalafx.scene.control.{Button, TextField}
import scalafx.stage.Stage

@sfxml
class RequestPlayerNameController (private val startGame: Button,
                                   private val backButton: Button,
                                   private val nameTextField : TextField){

  def getHome(event: ActionEvent): Unit = {
    MainApp.showHomePage()
  }

  def getGame(event: ActionEvent): Unit ={
    MainApp.showGamePage()
  }
}
