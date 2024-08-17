package assignment.POS.view

import assignment.POS.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafx.scene.image.ImageView
import scalafxml.core.macros.sfxml


@sfxml
class WelcomePageController (private val logIn: Button,
                             private val welcomePicture: ImageView){


  def getMain(event: ActionEvent): Unit = {
    MainApp.showMainPage()
  }
}
