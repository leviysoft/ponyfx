package leviysoft.ponyfx.views

import javafx.beans.value.{ObservableValue, ChangeListener}
import javafx.scene.control.TextField
import javafx.stage.Stage

import leviysoft.ponyfx.PonyApplication
import leviysoft.ponyfx.views.DialogResult.DialogResult

import scala.collection.mutable
import scala.reflect.runtime.universe._

abstract class View[T](val application: PonyApplication) extends Stage {
  protected var modelChanged: mutable.MutableList[T => Unit] = mutable.MutableList()

  private var vRes = DialogResult.None
  def viewResult = vRes

  private var modelField: T = _
  def model = modelField
  def model_=(value: T) = {
    modelField = value
    modelChanged.foreach(f => f(value))
  }

  protected def endWith(result: DialogResult): Unit = {
    vRes = result
    this.close()
  }

  protected def bind[PropType: TypeTag, TView <: View[T] : TypeTag](
    property: => PropType,
    propertySetter: PropType => Unit,
    textFieldGetter: TView => TextField): Unit = {
    val serializer = application.serializerOf[PropType]()
    val control = textFieldGetter(this.asInstanceOf[TView])
    control.textProperty().addListener(new ChangeListener[String] {
      override def changed(observable: ObservableValue[_ <: String], oldValue: String, newValue: String): Unit = {
        if (newValue != null) propertySetter(serializer.deserialize(newValue))
      }
    })
    val handler = (m: T) => control.setText(serializer.serialize(property))
    modelChanged += handler
    handler(model)
  }
}
