package assignment.POS.view
import assignment.POS.MainApp
import assignment.POS.model.{ShoppingCart,Accessory, ClothingItem, ClothingItemModel, Dress, Sellable}
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label, TableColumn, TableView}
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml
import scalafx.Includes._


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
                          private val materialValue: Label
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
  dressTable.selectionModel().selectedItemProperty().addListener { (_, _, selectedItem) =>
    showItemDetails(Option(selectedItem))
  }

  accessoryTable.selectionModel().selectedItemProperty().addListener { (_, _, selectedItem) =>
    showItemDetails(Option(selectedItem))
  }

  // Handle button actions
  def handleSizeCalculator(action: ActionEvent): Unit = {
    // Implement size calculator logic here
  }

  //add to cart
  def handleAddToCart(action: ActionEvent): Unit = {
    val selectedDress = dressTable.selectionModel().selectedItem.value
    val selectedAccessory = accessoryTable.selectionModel().selectedItem.value

    // Clear selection in both tables
    dressTable.selectionModel().clearSelection()
    accessoryTable.selectionModel().clearSelection()

    if (selectedDress != null) {
      ShoppingCart.instance.addItem(selectedDress)
      println(s"Added selected dress to cart: ${selectedDress.name}")
    } else if (selectedAccessory != null) {
      ShoppingCart.instance.addItem(selectedAccessory)
      println(s"Added selected accessory to cart: ${selectedAccessory.name}")
    } else {
      println("No item selected or multiple items selected")
    }

    // Print the cart contents to the console
    ShoppingCart.instance.printCart()
  }
}