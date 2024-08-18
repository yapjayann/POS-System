package assignment.POS.view

import assignment.POS.model.{Checkout, ShoppingCart}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, ChoiceBox, Label, TextField}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class CheckoutPageDialogController(private val totalAmount: Label,
                                   private val userAmount: TextField,
                                   private val voucherChoice: ChoiceBox[String],
                                   private val cancelPaymentButton: Button,
                                   private val confirmPaymentButton: Button
                            ) {

  var dialogStage: Stage = _
  private var _checkoutSuccessful: Boolean = false
  private val checkout = new Checkout()
  private var originalTotal: Double = 0.0

  def initialize(): Unit = {
    voucherChoice.items = ObservableBuffer(checkout.vouchers.map(_.name))
    voucherChoice.selectionModel().selectFirst()

    // Add a listener to update the total amount when a voucher is selected
    voucherChoice.selectionModel().selectedItemProperty().addListener { (_, _, newValue) =>
      updateTotalAmount(newValue)
    }
  }

  def setTotalAmount(amount: Double): Unit = {
    originalTotal = amount
    totalAmount.text = f"$$$amount%.2f"
    updateTotalAmount(voucherChoice.value())
  }

  private def updateTotalAmount(selectedVoucherName: String): Unit = {
    val selectedVoucher = checkout.vouchers.find(_.name == selectedVoucherName).getOrElse(checkout.vouchers.head)
    val finalTotal = checkout.calculateTotal(originalTotal, selectedVoucher)
    totalAmount.text = f"$$$finalTotal%.2f"
  }

  def handleConfirmPayment(action: ActionEvent): Unit = {
    try {
      val total = totalAmount.text().drop(1).toDouble // Drop the "$" symbol and parse the amount
      val paid = userAmount.text().toDouble
      val selectedVoucher = checkout.vouchers.find(_.name == voucherChoice.value()).getOrElse(checkout.vouchers.head)
      val finalTotal = checkout.calculateTotal(originalTotal, selectedVoucher)

      if (paid < finalTotal) {
        new Alert(AlertType.Error) {
          title = "Insufficient Payment"
          headerText = "You have entered an insufficient amount."
          contentText = f"Please pay $$${finalTotal - paid}%.2f more."
        }.showAndWait()
      } else {
        val change = checkout.calculateChange(finalTotal, paid)
        new Alert(AlertType.Confirmation) {
          title = "Payment Successful"
          headerText = "You have successfully checked out"
          contentText = f"Change: $$$change%.2f"
        }.showAndWait()
        _checkoutSuccessful = true
        ShoppingCart.instance.clearCart()
        dialogStage.close()
      }
    } catch {
      case _: NumberFormatException =>
        new Alert(AlertType.Error) {
          title = "Invalid Input"
          headerText = "You have entered invalid inputs"
          contentText = "Please enter a valid amount."
        }.showAndWait()
    }
  }

  def handleCancelPayment(action: ActionEvent): Unit = {
    dialogStage.close()
  }

  def checkoutSuccessful: Boolean = _checkoutSuccessful

  initialize()
}