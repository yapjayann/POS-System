package assignment.game.model

abstract class GameObject(val position: (Double, Double)) {
  def render: Unit
}


