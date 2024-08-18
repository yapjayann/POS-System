package assignment.POS.view

import assignment.POS.model.SizeChart
import scalafx.scene.control.{Label, TextField}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml


@sfxml
class CalculateSizeDialogController(private val shoulderValue: TextField,
                                    private val bustValue: TextField,
                                    private val waistValue: TextField,
                                    private val hipValue: TextField,
                                    private val recommendedSizeValue: Label
                             ) {
  // Reference to the dialog stage
  var dialogStage: Stage = _

  // Handles the Cancel button action by closing the dialog
  def handleCancel(): Unit = {
    dialogStage.close()
  }

  // Handles the Calculate Size button action
  def handleCalculateSize(): Unit = {
    try {
      val shoulder = shoulderValue.text.value.toDouble
      val bust = bustValue.text.value.toDouble
      val waist = waistValue.text.value.toDouble
      val hip = hipValue.text.value.toDouble

      // Create a SizeChart instance and calculate the recommended size
      val sizeChart = SizeChart(shoulder, bust, waist, hip)
      val recommendedSize = sizeChart.recommendSize

      // Display the recommended size
      recommendedSizeValue.text = recommendedSize
    } catch {
      // Exception handling - Handle invalid input by displaying an error message
      case _: NumberFormatException =>
        recommendedSizeValue.text = "Invalid input."
    }
  }

}
