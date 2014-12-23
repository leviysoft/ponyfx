package leviysoft.ponyfx.views

import javafx.stage.Stage

import leviysoft.ponyfx.views.DialogResult.DialogResult

import scala.collection.mutable

abstract class View[T] extends Stage {
  protected var modelChanged: mutable.MutableList[T => Unit] = mutable.MutableList()

  private var vRes = DialogResult.None
  def viewResult = vRes

  private var modelField: T = throw new IllegalArgumentException()
  def model = modelField
  def model_=(value: T) = {
    modelField = value
    modelChanged.foreach(f => f(value))
  }

  protected def endWith(result: DialogResult): Unit = {
    vRes = result
    this.close()
  }

  protected def bind(): Unit = {

  }
}
