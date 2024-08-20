package assignment.POS.model


// Trait for all sellable items
trait Sellable {
  def id: String
  def name: String
  def price: Double
  def imagePath: String
}
