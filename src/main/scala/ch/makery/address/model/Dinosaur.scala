package ch.makery.address.model

case class Dinosaur(var position: (Double, Double), var isJumping: Boolean, var velocity: Double) extends GameObject {
  val dinosaurWidth: Double = 50
  val dinosaurHeight: Double = 50
}
