package leviysoft.ponyfx

import leviysoft.ponyfx.di.Container
import leviysoft.ponyfx.serialization.Serializer
import leviysoft.ponyfx.views.DialogResult.DialogResult
import leviysoft.ponyfx.views.SimpleView

class PonyApplication(private val container: Container) extends TPonyApplication{
  override def create[T](): OperationResult[T] = ???

  override def getSerializer[T]: Serializer[T] = ???

  override def edit[T](model: T): OperationResult[T] = ???

  override def show[TView <: SimpleView](): DialogResult = ???
}
