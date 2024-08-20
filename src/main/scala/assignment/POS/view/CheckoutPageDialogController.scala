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

  var dialogStage: Stage = _ // Stage to hold the dialog
  private var _checkoutSuccessful: Boolean = false // Flag to indicate if checkout was successful
  private val checkout = new Checkout() // Instance of the Checkout class to handle payment logic
  private var originalTotal: Double = 0.0 // Variable to store the original total amount before applying any vouchers

  // Initialize the voucher choice box with available vouchers and set up a listener for voucher selection
  private def initialize(): Unit = {
    voucherChoice.items = ObservableBuffer(checkout.vouchers.map(_.name))
    voucherChoice.selectionModel().selectFirst() // Select the first voucher by default

    // Add a listener to update the total amount when a voucher is selected
    voucherChoice.selectionModel().selectedItemProperty().addListener { (_, _, newValue) =>
      updateTotalAmount(newValue) // Update the total amount based on the selected voucher
    }
  }

  // Set the total amount to be paid, and update the display with the selected voucher applied
  def setTotalAmount(amount: Double): Unit = {
    originalTotal = amount // Store the original total amount
    totalAmount.text = f"$$$amount%.2f" // Display the original total amount
    updateTotalAmount(voucherChoice.value()) // Update the total amount based on the selected voucher
  }

  // Update the displayed total amount based on the selected voucher
  private def updateTotalAmount(selectedVoucherName: String): Unit = {
    val selectedVoucher = checkout.vouchers.find(_.name == selectedVoucherName).getOrElse(checkout.vouchers.head) // Find the selected voucher
    val finalTotal = checkout.calculateTotal(originalTotal, selectedVoucher) // Calculate the final total with the voucher applied
    totalAmount.text = f"$$$finalTotal%.2f" // Update the total amount label with the calculated total
  }

  // Handle the event when the user confirms the payment
  def handleConfirmPayment(action: ActionEvent): Unit = {
    try {
      val total = totalAmount.text().drop(1).toDouble // Drop the "$" symbol and parse the amount
      val paid = userAmount.text().toDouble // Parse the user's payment amount
      val selectedVoucher = checkout.vouchers.find(_.name == voucherChoice.value()).getOrElse(checkout.vouchers.head) // Find the selected voucher
      val finalTotal = checkout.calculateTotal(originalTotal, selectedVoucher) // Calculate the final total with the voucher applied

      // Check if the payment is insufficient
      if (paid < finalTotal) {
        new Alert(AlertType.Error) { // Show an error alert
          title = "Insufficient Payment"
          headerText = "You have entered an insufficient amount."
          contentText = f"Please pay $$${finalTotal - paid}%.2f more."
        }.showAndWait()
      } else { // If payment is sufficient
        val change = checkout.calculateChange(finalTotal, paid) // Calculate the change
        new Alert(AlertType.Information) {
          title = "Payment Successful"
          headerText = "You have successfully checked out"
          contentText = f"Change: $$$change%.2f"
        }.showAndWait()
        _checkoutSuccessful = true
        ShoppingCart.instance.clearCart()
        dialogStage.close()
      }
    } catch {
      case _: NumberFormatException => // Handle invalid input
        new Alert(AlertType.Error) {
          title = "Invalid Input"
          headerText = "You have entered invalid inputs"
          contentText = "Please enter a valid amount."
        }.showAndWait()
    }
  }

  // Handle the event when the user cancels the payment
  def handleCancelPayment(action: ActionEvent): Unit = {
    dialogStage.close()
  }

  // Getter to check if checkout was successful
  def checkoutSuccessful: Boolean = _checkoutSuccessful

  // Initialize the controller when it's created
  initialize()
}