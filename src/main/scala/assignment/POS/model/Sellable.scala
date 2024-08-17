package assignment.POS.model
import scalafx.scene.image.Image

// Trait for all sellable items
trait Sellable {
  def id: String
  def name: String
  def price: Double
  def imagePath: String
}
