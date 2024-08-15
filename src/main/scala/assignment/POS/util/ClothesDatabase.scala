/**package assignment.POS.util

import assignment.POS.model.{Accessory, CartItem, ClothingItem, Dress, Sellable}
import scalafx.scene.image.Image
import scalikejdbc._

trait ClothesDatabase {

  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:myDB;create=true;"

  // Initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL, "me", "mine")

  implicit val session: AutoSession.type = AutoSession

  def setupDB(): Unit

  def hasDBInitialized: Boolean
}

object Database extends ClothesDatabase {

  def setupDB(): Unit = {
    if (!hasDBInitialized) {
      initializeTables()
    }
  }

  def hasDBInitialized: Boolean = {
    DB.getTable("CartItem") match {
      case Some(_) => true
      case None => false
    }
  }

  private def initializeTables(): Unit = {
    DB.autoCommit { implicit session =>
      sql"""
        CREATE TABLE ClothingItem (
          id VARCHAR(64),
          name VARCHAR(255),
          price DOUBLE,
          size CHAR,
          material VARCHAR(255),
          type VARCHAR(20),
          imagePath VARCHAR(255),
          PRIMARY KEY(id, size, material)
        )
      """.execute.apply()

      sql"""
        CREATE TABLE CartItem (
          id VARCHAR(64),
          name VARCHAR(255),
          price DOUBLE,
          imagePath VARCHAR(255),
          quantity INT,
          PRIMARY KEY(id)
        )
      """.execute.apply()
    }
  }

  // Insert methods for clothing items
  def insertDress(dress: Dress): Unit = {
    DB.autoCommit { implicit session =>
      sql"""
        INSERT INTO ClothingItem (id, name, price, size, type, imagePath)
        VALUES (${dress.id}, ${dress.name}, ${dress.price}, ${dress.size}, 'Dress', ${dress.imagePath})
      """.update.apply()
    }
  }

  def insertAccessory(accessory: Accessory): Unit = {
    DB.autoCommit { implicit session =>
      sql"""
        INSERT INTO ClothingItem (id, name, price, material, type, imagePath)
        VALUES (${accessory.id}, ${accessory.name}, ${accessory.price}, ${accessory.material}, 'Accessory', ${accessory.imagePath})
      """.update.apply()
    }
  }
}
**/
