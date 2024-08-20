package assignment.POS.util

import assignment.POS.model.{Accessory, CartItem, Dress, Sellable}
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

  // Method to create the "cart_items" table in the database
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

  // Method to save cart items to the database
  // Generic programming - can store "_" as long as it extends Sellable trait
  def saveCartItems(items: Seq[CartItem[_ <: Sellable]]): Unit = {
    DB.localTx { implicit session =>
      // Clear existing items
      sql"DELETE FROM cart_items".update.apply()

      // Insert each item in the provided sequence into the table
      items.foreach { item =>
        // Determine the item type based on the class name of the item
        val itemType = item.item.getClass.getSimpleName
        // Extract size or material depending on whether the item is a Dress or Accessory
        val (size, material) = item.item match {
          case dress: Dress => (Some(dress.size), None)
          case accessory: Accessory => (None, Some(accessory.material))
          case _ => (None, None)
        }

        // Execute the SQL command to insert the item into the table
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

  // Method to load cart items from the database
  def loadCartItems(): Seq[CartItem[_ <: Sellable]] = {
    DB.readOnly { implicit session =>
      sql"SELECT * FROM cart_items".map { rs =>
        // Extract the values from each row into variables
        val id = rs.string("id")
        val itemType = rs.string("item_type")
        val quantity = rs.int("quantity")
        val name = rs.string("name")
        val price = rs.double("price")
        val imagePath = rs.string("image_path")

        // Match the item type to construct the appropriate Sellable item
        val item: Sellable = itemType match {
          case "Dress" =>
            Dress(id, name, price, imagePath, rs.string("size"))
          case "Accessory" =>
            Accessory(id, name, price, imagePath, rs.string("material"))
          case _ => throw new IllegalStateException(s"Unknown item type: $itemType")
        }

        // Return a CartItem containing the constructed item and its quantity
        CartItem(item, quantity)
      }.list.apply() // Convert the result set to a list of CartItems
    }
  }
}