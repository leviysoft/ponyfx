package leviysoft.ponyfx.controllerTraits

import leviysoft.ponyfx.OperationResult
import leviysoft.ponyfx.views.StronglyTypedView

trait CanCreate[T] {
  def create(view: StronglyTypedView[T]): OperationResult[T]
}
