package assignment.POS.view

import assignment.POS.model.SizeChart
import scalafx.scene.control.{Label, TextField}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml
import scala.util.{Try, Success, Failure}

@sfxml
class CalculateSizeController(private val shoulderValue: TextField,
                              private val bustValue: TextField,
                              private val waistValue: TextField,
                              private val hipValue: TextField,
                              private val recommendedSizeValue: Label
                             ) {
  var dialogStage: Stage = _

  def handleCancel(): Unit = {
    dialogStage.close()
  }

  def handleCalculateSize(): Unit = {
    try {
      val shoulder = shoulderValue.text.value.toDouble
      val bust = bustValue.text.value.toDouble
      val waist = waistValue.text.value.toDouble
      val hip = hipValue.text.value.toDouble

      val sizeChart = SizeChart(shoulder, bust, waist, hip)
      val recommendedSize = sizeChart.recommendSize

      recommendedSizeValue.text = recommendedSize
    } catch {
      case _: NumberFormatException =>
        recommendedSizeValue.text = "Invalid input. Please enter numeric values."
    }
  }

}
