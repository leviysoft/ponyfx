package leviysoft.ponyfx.views

import leviysoft.ponyfx.views.DialogResult.DialogResult

trait SimpleView {
  val viewResult: DialogResult
  def show(): Unit
  def showAndWait(): Unit
}
