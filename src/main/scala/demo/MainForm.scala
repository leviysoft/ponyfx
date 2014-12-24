package demo

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.{Button, ListView}
import javafx.stage.Stage

import demo.domain.Item
import leviysoft.ponyfx.PonyApplication
import leviysoft.ponyfx.util.EventListenerConstructor._
import leviysoft.ponyfx.views.DialogResult.DialogResult
import leviysoft.ponyfx.views.{DialogResult, SimpleView}

class MainForm(application: PonyApplication) extends Stage with SimpleView {
  override val viewResult: DialogResult = DialogResult.OK
  @FXML private var todoList: ListView[String] = null
  @FXML private var createButton: Button = null

  def initialize(): Unit = {
    createButton.setOnAction((event: ActionEvent) => create(event))
  }

  def create(event: ActionEvent): Unit = {
    val result = application.create[Item]()
  }
}
