package ch.makery.address.model

abstract class ClothingItem(val id: String) extends Sellable{
  var name: String = _
  var quantity: Int = _
  var price: Double = _
}

class Dress (_id: String)extends ClothingItem(_id){
  var size: Char = _
  //override def
}

class Accessory(_id: String) extends ClothingItem(_id){
  var material: String = _
  //override def
}