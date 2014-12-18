package demo.views

import javafx.fxml.FXML
import javafx.scene.control.TextField

import demo.domain.Item
import leviysoft.ponyfx.views.DialogResult.DialogResult
import leviysoft.ponyfx.views.{DialogResult, StronglyTypedView, View}

class ItemEditor extends View[Item] with StronglyTypedView[Item] {
  override var model: Item = _
  override val viewResult: DialogResult = DialogResult.None
  @FXML var idEditor: TextField = null
  @FXML var nameEditor: TextField = null
  @FXML var amountEditor: TextField = null
}
