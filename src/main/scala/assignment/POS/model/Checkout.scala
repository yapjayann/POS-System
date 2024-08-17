package assignment.POS.model

// Case class to represent Voucher
case class Voucher(name: String, apply: Double => Double)

// For Checkout logic
class Checkout {
  val vouchers: List[Voucher] = List(
    Voucher("No Discount", amount => amount),
    Voucher("$20 Off", amount => Math.max(amount - 20, 0)),
    Voucher("10% Discount", amount => amount * 0.9),
    Voucher("15% Discount", amount => amount * 0.85)
  )

  def calculateTotal(amount: Double, voucher: Voucher): Double = {
    Math.max(voucher.apply(amount), 0)
  }

  def calculateChange(total: Double, paid: Double): Double = {
    Math.max(paid - total, 0)
  }
}
