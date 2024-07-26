package ch.makery.address.view
import ch.makery.address.MainApp
import scalafx.animation.AnimationTimer
import scalafx.scene.input.KeyCode
import scalafx.stage.Stage
import scalafx.Includes._
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label}


@sfxml
class GamePageController (private val backButton: Button,
                          private val playerName: Label,
                          private val highScore: Label,
                          private val currentScore: Label){
  def getHome(event: ActionEvent): Unit = {
    MainApp.showHomePage()
  }

 // private def checkGameOver(): Unit ={
   // if (dinoDead){
      //stop game loop
   // }
 // }
}
