package ch.makery.address.model

class Player (val name: String) {
  var currentScore: Int = 0
  var highScore: Int = 0


  def getCurrentScore(): Int ={
    currentScore
  }

  def getHighScore(): Int = {
    highScore
  }


  def updateHighScore(): Unit = {
    if (currentScore > highScore) {
      highScore = currentScore
    }
  }
}
