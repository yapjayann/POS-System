package ch.makery.address.view
import ch.makery.address.MainApp
import scalafx.Includes._
import scalafxml.core.macros.sfxml
import scalafx.scene.control.Button
import scalafx.stage.Stage
import scalafx.event.ActionEvent


@sfxml
class HomePageController (private val startGame: Button, private val howToPlay: Button){


  def getRequestPlayerName(event: ActionEvent): Unit = {
    MainApp.showRequestPlayerName()
  }

  def getHowToPlay(event: ActionEvent): Unit = {
    MainApp.showHowToPlay()
  }
}
