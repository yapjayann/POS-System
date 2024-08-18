package assignment.POS.view
import assignment.POS.MainApp
import assignment.POS.model.{ShoppingCart,Accessory, ClothingItem, ClothingItemModel, Dress, Sellable}
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label, TableColumn, TableView,TabPane}
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scala.util.{Try, Success, Failure}


@sfxml
class MainPageController (private val sizeCalculatorButton: Button,
                          private val dressTable: TableView[Dress],
                          private val accessoryTable: TableView[Accessory],
                          private val dressNameColumn: TableColumn[Dress, String],
                          private val dressIDColumn: TableColumn[Dress, String],
                          private val accessoryNameColumn: TableColumn[Accessory, String],
                          private val accessoryIDColumn: TableColumn[Accessory, String],
                          private val clothingItemImage: ImageView,
                          private val itemNameValue: Label,
                          private val itemIDValue: Label,
                          private val priceValue: Label,
                          private val sizeValue: Label,
                          private val materialValue: Label,
                          private val tabPane: TabPane
                         ) {

  // Initialize the tables with data from MainApp
  dressTable.items = ClothingItemModel.dressItems
  accessoryTable.items = ClothingItemModel.accessoryItems

  // Set up cell value factories for dress table
  dressNameColumn.cellValueFactory = { cellData =>
    StringProperty(cellData.value.name) // Directly use name without .value
  }

  dressIDColumn.cellValueFactory = { cellData =>
    StringProperty(cellData.value.id) // Directly use id without .value
  }

  // Set up cell value factories for accessory table
  accessoryNameColumn.cellValueFactory = { cellData =>
    StringProperty(cellData.value.name) // Directly use name without .value
  }

  accessoryIDColumn.cellValueFactory = { cellData =>
    StringProperty(cellData.value.id) // Directly use id without .value
  }

  //Variables to store selected items
  private var selectedDress: Option[Dress] = None
  private var selectedAccessory: Option[Accessory] = None

  // Show details of the selected item
  private def showItemDetails(item: Option[ClothingItem]): Unit = {
    item match {
      case Some(clothingItem) =>
        clothingItemImage.image = new Image(clothingItem.imagePath)
        itemNameValue.text = clothingItem.name
        itemIDValue.text = clothingItem.id
        priceValue.text = f"$$${clothingItem.price}%.2f"

        clothingItem match {
          case dress: Dress =>
            sizeValue.visible = true
            materialValue.visible = false
            sizeValue.text = s"${dress.size}"

          case accessory: Accessory =>
            sizeValue.visible = false
            materialValue.visible = true
            materialValue.text = accessory.material
        }

      case None =>
        // Clear details if no item is selected
        clothingItemImage.image = null
        itemNameValue.text = ""
        itemIDValue.text = ""
        priceValue.text = ""
        sizeValue.text = ""
        materialValue.text = ""
    }
  }

  // Set up listeners for table selections
  dressTable.selectionModel().selectedItem.onChange { (_, _, newValue) =>
    selectedDress = Option(newValue)
    selectedAccessory = None
    showItemDetails(selectedDress)
  }

  accessoryTable.selectionModel().selectedItem.onChange { (_, _, newValue) =>
    selectedAccessory = Option(newValue)
    selectedDress = None
    showItemDetails(selectedAccessory)
  }

  // Add a listener to the tab selection
  tabPane.selectionModel().selectedItem.onChange { (_, _, newTab) =>
    if (newTab.getText == "Dresses") {
      accessoryTable.selectionModel().clearSelection()
      selectedAccessory = None
    } else {
      dressTable.selectionModel().clearSelection()
      selectedDress = None
    }
    showItemDetails(None)
  }

  // Add to cart
  def handleAddToCart(action: ActionEvent): Unit = {
    val itemToAdd: Option[Sellable] = selectedDress.orElse(selectedAccessory)

    itemToAdd match {
      case Some(item) =>
        ShoppingCart.instance.addItem(item)
        println(s"Added selected item to cart: ${item.name}")

        // Show a confirmation message
        val alert = new Alert(AlertType.Information) {
          title = "Item Added"
          headerText = "Success"
          contentText = s"Added selected item to cart: ${item.name}"
        }
        alert.showAndWait()

        // Clear selections
        dressTable.selectionModel().clearSelection()
        accessoryTable.selectionModel().clearSelection()
        selectedDress = None
        selectedAccessory = None
        showItemDetails(None)

      case None =>
        println("No item selected")
        // Show an error message if no item is selected
        val alert = new Alert(AlertType.Error) {
          title = "Selection Error"
          headerText = "Please select an item."
          contentText = "No item selected."
        }
        alert.showAndWait()
    }

    // Print the cart contents to the console
    ShoppingCart.instance.printCart()
  }

  def getSizeCalculator(): Unit = {
    MainApp.showCalculateSizeDialog()
  }
}