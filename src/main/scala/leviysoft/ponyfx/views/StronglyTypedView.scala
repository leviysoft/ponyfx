package leviysoft.ponyfx.views

trait StronglyTypedView[T] {
  val model: T
  def show(): Unit
  def showAndWait(): Unit
}
