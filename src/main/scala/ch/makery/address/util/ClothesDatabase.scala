package ch.makery.address.util
import scalikejdbc._ //third party library to make connection to database
import ch.makery.address.model.ClothingItem

trait ClothesDatabase {
  val derbyDriverClassname = "org.apache.derby.jdbc.EmbeddedDriver" //define DB class
  //apache derby is an embedded database that runs with ur application (vs server based like oracle)

  val dbURL = "jdbc:derby:myDB;create=true;"; //db url similar to http protocol
  //protocol point to database ^
  // initialize JDBC driver & connection pool
  Class.forName(derbyDriverClassname) //load the driver class to JVM

  ConnectionPool.singleton(dbURL, "me", "mine") //url, username, password

  // ad-hoc session provider on the REPL
  //implicit = default value
  implicit val session = AutoSession


}
object Database extends ClothesDatabase {
  def setupDB(): Unit = {
    if (!hasDBInitialized) initializeTable()
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
          quantity INT,
          price DOUBLE,
          size CHAR,
          material VARCHAR(255)
        )
      """.execute.apply()
    }
  }
}