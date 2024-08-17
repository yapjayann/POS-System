package assignment.POS.util

import assignment.POS.model.{Accessory, CartItem, ClothingItem, Dress, Sellable}
import scalafx.collections.ObservableBuffer
import scalikejdbc._

trait CartDatabase {

  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver"
  val dbURL = "jdbc:derby:myDB;create=true;"

  // Initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname)
  ConnectionPool.singleton(dbURL, "me", "mine")

  implicit val session: AutoSession.type = AutoSession

  // Check if the database has been initialized
  def hasDBInitialized: Boolean = {
    DB.getTable("cart_items").isDefined
  }

  // Database setup method
  def setupDB(): Unit = {
    if (!hasDBInitialized) {
      setupCartTable()
    }
  }

  private def setupCartTable(): Unit = {
    DB.autoCommit { implicit session =>
      sql"""
        CREATE TABLE cart_items (
          id VARCHAR(255) PRIMARY KEY,
          item_type VARCHAR(50),
          quantity INT,
          name VARCHAR(255),
          price DOUBLE,
          image_path VARCHAR(255),
          size VARCHAR(50),
          material VARCHAR(255)
        )
      """.execute.apply()
    }
  }

  // Cart database operations
  def saveCartItems(items: Seq[CartItem[_ <: Sellable]]): Unit = {
    DB.localTx { implicit session =>
      // Clear existing items
      sql"DELETE FROM cart_items".update.apply()

      // Insert new items
      items.foreach { item =>
        val itemType = item.item.getClass.getSimpleName
        val (size, material) = item.item match {
          case dress: Dress => (Some(dress.size), None)
          case accessory: Accessory => (None, Some(accessory.material))
          case _ => (None, None)
        }

        sql"""
          INSERT INTO cart_items
          (id, item_type, quantity, name, price, image_path, size, material)
          VALUES
          (${item.item.id}, ${itemType}, ${item.quantity}, ${item.item.name},
           ${item.item.price}, ${item.item.imagePath}, ${size}, ${material})
        """.update.apply()
      }
    }
  }

  def loadCartItems(): Seq[CartItem[_ <: Sellable]] = {
    DB.readOnly { implicit session =>
      sql"SELECT * FROM cart_items".map { rs =>
        val id = rs.string("id")
        val itemType = rs.string("item_type")
        val quantity = rs.int("quantity")
        val name = rs.string("name")
        val price = rs.double("price")
        val imagePath = rs.string("image_path")

        val item: Sellable = itemType match {
          case "Dress" =>
            Dress(id, name, price, imagePath, rs.string("size"))
          case "Accessory" =>
            Accessory(id, name, price, imagePath, rs.string("material"))
          case _ => throw new IllegalStateException(s"Unknown item type: $itemType")
        }

        CartItem(item, quantity)
      }.list.apply()
    }
  }
}