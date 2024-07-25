package ch.makery.address.model

class GameModel {
  var dinosaur: Dinosaur
  var obstacles: List[Cactus] = List()
  var score: Int = 0
  var isGameOver: Boolean = false

  def update()
  def checkCollision()
  def updateScore(): Unit = {
    score += 1
  }
}
