package ch.makery.address.view

import ch.makery.address.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.Button
import scalafxml.core.macros.sfxml

@sfxml
class WelcomePageController (private val logIn: Button){

  def getMain(event: ActionEvent): Unit = {
    MainApp.showMainPage()
  }
}
