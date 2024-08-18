package assignment.POS.model

// Case class to represent a Voucher
// 'apply' take total amount as input and returns the discounted amount
case class Voucher(name: String, apply: Double => Double)
