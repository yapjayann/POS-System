package assignment.POS.view

import scalafx.scene.control.{Button, TextField}
import scalafx.stage.Stage
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafxml.core.macros.sfxml

@sfxml
class EditQuantityDialogController(private val newQuantityValue: TextField,
                                   private val okButton: Button,
                                   private val cancelButton: Button){

  var dialogStage: Stage = _ // Stage for the dialog
  var resultCallback: Int => Unit = _ // Callback to handle the result

  // Set the initial quantity value in the TextField
  def setInitialQuantity(quantity: Int): Unit = {
    newQuantityValue.text = quantity.toString
  }

  // Handle OK button click
  def handleOk(): Unit = {
    try {
      val newQuantity = newQuantityValue.text.value.toInt
      // Validate the input
      if (newQuantity < 0) {
        new Alert(AlertType.Error) {
          title = "Invalid Input"
          headerText = "Negative number entered"
          contentText = "Please enter a non-negative number"
        }.showAndWait()
      } else {
        resultCallback(newQuantity) // Call the result callback with the new quantity
        dialogStage.close()
      }
    } catch {
      case _: NumberFormatException =>
        new Alert(AlertType.Error) {
          title = "Invalid Input"
          headerText = "You have entered invalid input"
          contentText = "Please enter a valid number."
        }.showAndWait()
    }
  }

  // Handle Cancel button click
  def handleCancel(): Unit = {
    dialogStage.close()
  }
}