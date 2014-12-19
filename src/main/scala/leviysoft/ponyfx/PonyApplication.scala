package leviysoft.ponyfx

import javafx.stage.Stage

import leviysoft.ponyfx.serialization.Serializer
import leviysoft.ponyfx.views.DialogResult.DialogResult
import leviysoft.ponyfx.views.SimpleView

import scala.reflect.runtime.universe._

trait PonyApplication {
  def create[T](): OperationResult[T]
  def edit[T](model: T): OperationResult[T]
  def show[TView <: Stage with SimpleView : TypeTag](): DialogResult
  def getSerializer[T]: Serializer[T]
}
