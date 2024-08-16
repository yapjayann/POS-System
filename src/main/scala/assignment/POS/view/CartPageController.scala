package assignment.POS.view
import assignment.POS.model.{CartItem,Sellable}
import scalafx.Includes._
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{Button, Label, TableColumn, TableView}
import scalafxml.core.macros.sfxml

@sfxml
class CartPageController(private val cartTable: TableView[CartItem],
                         private val itemColumn: TableColumn[CartItem, String],
                         private val quantityColumn: TableColumn[CartItem, Int],
                         private val totalAmountValue: Label,
                         private val itemNameValue: Label,
                         private val itemIDValue: Label,
                         private val priceValue: Label,
                         private val sizeValue: Label,
                         private val materialValue: Label,
                         private val quantityValue: Label,
                         private val editQuantityButton: Button,
                         private val removeButton: Button,
                         private val checkOutButton: Button
                        ) {
}
