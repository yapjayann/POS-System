package ch.makery.address.view
import ch.makery.address.MainApp
import scalafx.Includes._
import scalafxml.core.macros.sfxml
import scalafx.scene.control.Button
import scalafx.stage.Stage


@sfxml
class HomePageController (private val startGame: Button, private val howToPlay: Button){


  def getRequestPlayerName(): Unit = {
    MainApp.showRequestPlayerName()
  }

  def getHowToPlay(): Unit = {
    MainApp.showHowToPlay()
  }
}
