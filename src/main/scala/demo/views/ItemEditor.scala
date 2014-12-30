package demo.views

import javafx.fxml.FXML
import javafx.scene.control.TextField

import demo.domain.Item
import leviysoft.ponyfx.PonyApplication
import leviysoft.ponyfx.views.DialogResult.DialogResult
import leviysoft.ponyfx.views.{DialogResult, StronglyTypedView, View}

class ItemEditor(application: PonyApplication) extends View[Item](application) with StronglyTypedView[Item] {
  override var model: Item = _
  override val viewResult: DialogResult = DialogResult.None
  @FXML var idEditor: TextField = null
  @FXML var nameEditor: TextField = null
  @FXML var amountEditor: TextField = null

  @FXML def initialize(): Unit = {
    bind(model.id, (i: Int) => model.id = i, (v: ItemEditor) => v.idEditor)
    bind(model.name, (i: String) => model.name = i, (v: ItemEditor) => v.nameEditor)
    bind(model.amount, (i: Int) => model.amount = i, (v: ItemEditor) => v.amountEditor)
  }
}
