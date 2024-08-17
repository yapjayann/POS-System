package assignment.POS.model
import scala.collection.immutable.List

// Case class to represent a Voucher
// 'apply' take total amount as input and returns the discounted amount
case class Voucher(name: String, apply: Double => Double)

// For Checkout logic
class Checkout {
  // A list of predefined vouchers available
  val vouchers: List[Voucher] = List(
    Voucher("No Discount", amount => amount),
    Voucher("$20 Off", amount => Math.max(amount - 20, 0)),
    Voucher("10% Discount", amount => amount * 0.9),
    Voucher("15% Discount", amount => amount * 0.85)
  )

  // Calculate total after applying the voucher
  def calculateTotal(amount: Double, voucher: Voucher): Double = {
    Math.max(voucher.apply(amount), 0)
  }

  // Calculate any change (paid-total)
  def calculateChange(total: Double, paid: Double): Double = {
    Math.max(paid - total, 0)
  }
}
