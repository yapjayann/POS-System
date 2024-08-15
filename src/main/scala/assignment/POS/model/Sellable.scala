package assignment.POS.model
import scalafx.scene.image.Image

trait Sellable {
  def id: String
  def name: String
  def price: Double
  def imagePath: String
}
