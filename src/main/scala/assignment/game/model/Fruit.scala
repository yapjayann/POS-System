package assignment.game.model

abstract class Fruit(position: (Double, Double)) extends GameObject(position) {
  def growAmount: Int
}

