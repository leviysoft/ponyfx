package demo

import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView}
import javafx.stage.Stage

import leviysoft.ponyfx.views.DialogResult.DialogResult
import leviysoft.ponyfx.views.{DialogResult, SimpleView}

class MainForm extends Stage with SimpleView {
  override val viewResult: DialogResult = DialogResult.OK
  @FXML private var todoList: ListView[String] = null
  @FXML private var createButton: Button = null


}
