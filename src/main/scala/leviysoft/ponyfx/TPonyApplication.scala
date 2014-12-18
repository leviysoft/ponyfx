package leviysoft.ponyfx

import com.sun.jnlp.ApiDialog.DialogResult
import leviysoft.ponyfx.serialization.Serializer
import leviysoft.ponyfx.views.SimpleView

trait TPonyApplication {
  def create[T](): OperationResult[T]
  def edit[T](model: T): OperationResult[T]
  def show[TView <: SimpleView](): DialogResult
  def getSerializer[T]: Serializer[T]
}
