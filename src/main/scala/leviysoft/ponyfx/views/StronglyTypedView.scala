package leviysoft.ponyfx.views

import leviysoft.ponyfx.views.DialogResult.DialogResult

trait StronglyTypedView[T] {
  var model: T
  val viewResult: DialogResult
  def show(): Unit
  def showAndWait(): Unit
}
