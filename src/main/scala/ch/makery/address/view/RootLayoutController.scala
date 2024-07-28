package ch.makery.address.view

import ch.makery.address.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.control.{MenuBar, MenuItem}
import scalafxml.core.macros.sfxml

@sfxml
class RootLayoutController (private val shop: MenuItem, private val viewCart: MenuItem, private val logOut:MenuItem){

  def getMain(event: ActionEvent): Unit = {
    MainApp.showMainPage()
  }
  def getCart(event: ActionEvent): Unit = {
    MainApp.showCartPage()
  }
  def getWelcome(event: ActionEvent): Unit = {
    MainApp.showWelcomePage()
  }
}
