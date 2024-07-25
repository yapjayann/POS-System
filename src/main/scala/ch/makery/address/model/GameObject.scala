import scalafx.scene.image.Image

trait GameObject {
  def position: (Double, Double)
  def width: Double
  def height: Double
  def update(deltaTime: Double): Unit
  def image: Image
}

