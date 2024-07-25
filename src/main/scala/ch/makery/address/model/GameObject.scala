package ch.makery.address.model

trait GameObject {
  def position: (Double, Double)
  def width: Double
  def height: Double
  def update(deltaTime: Double): Unit
}
