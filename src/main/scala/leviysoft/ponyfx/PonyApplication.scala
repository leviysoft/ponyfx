package leviysoft.ponyfx

import leviysoft.ponyfx.serialization.Serializer
import leviysoft.ponyfx.views.DialogResult.DialogResult
import leviysoft.ponyfx.views.SimpleView

trait PonyApplication {
  def create[T](): OperationResult[T]
  def edit[T](model: T): OperationResult[T]
  def show[TView <: SimpleView](): DialogResult
  def getSerializer[T]: Serializer[T]
}
