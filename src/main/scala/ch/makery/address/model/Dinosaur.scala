package ch.makery.address.model

import scalafx.scene.image.Image

case class Dinosaur(var position: (Double, Double), var isJumping: Boolean, var velocityY: Double) extends GameObject {
  val width: Double = 88
  val height: Double = 94
  var currentImage: Image = new Image("dino.png")



  def jump(): Unit = {
  }


  def image: Image = currentImage
}

