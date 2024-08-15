package ch.makery.address.util

import scalikejdbc._
import ch.makery.address.model.{ClothingItem, Dress, Accessory}

trait ClothesDatabase {

  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:myDB;create=true;"

  // Initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL, "me", "mine")

  implicit val session: AutoSession.type = AutoSession

  def setupDB(): Unit = {
    if (!hasDBInitialized) {
      initializeTable()
    }
  }

  def hasDBInitialized: Boolean = {
    DB getTable "ClothingItem" match {
      case Some(_) => true
      case None => false
    }
  }

  def initializeTable(): Unit = {
    DB autoCommit { implicit session =>
      sql"""
        CREATE TABLE ClothingItem (
          id VARCHAR(64) PRIMARY KEY,
          name VARCHAR(255),
          price DOUBLE,
          size CHAR,
          material VARCHAR(255),
          type VARCHAR(20)
        )
      """.execute.apply()
    }
  }


  def insertDress(dress: Dress): Unit = {
    DB autoCommit { implicit session =>
      sql"""
        INSERT INTO ClothingItem (id, name, price, size, type)
        VALUES (${dress.id}, ${dress.name}, ${dress.price}, ${dress.size}, 'Dress')
      """.update.apply()
    }
  }

  def insertAccessory(accessory: Accessory): Unit = {
    DB autoCommit { implicit session =>
      sql"""
        INSERT INTO ClothingItem (id, name, price, material, type)
        VALUES (${accessory.id}, ${accessory.name}, ${accessory.price}, ${accessory.material}, 'Accessory')
      """.update.apply()
    }
  }

  def updateStock(item: ClothingItem, newQuantity: Int): Unit = {
    DB autoCommit { implicit session =>
      sql"""
        UPDATE ClothingItem SET quantity = $newQuantity WHERE id = ${item.id}
      """.update.apply()
    }
  }


  def fetchAllDresses(): List[Dress] = {
    DB readOnly { implicit session =>
      sql"SELECT * FROM ClothingItem WHERE type = 'Dress'"
        .map(rs => Dress(
          id = rs.string("id"),
          name = rs.string("name"),
          price = rs.double("price"),
          size = rs.string("size").charAt(0)
        )).list.apply()
    }
  }


  def fetchAllAccessories(): List[Accessory] = {
    DB readOnly { implicit session =>
      sql"SELECT * FROM ClothingItem WHERE type = 'Accessory'"
        .map(rs => Accessory(
          id = rs.string("id"),
          name = rs.string("name"),
          price = rs.double("price"),
          material = rs.string("material")
        )).list.apply()
    }
  }
}

object Database extends ClothesDatabase
