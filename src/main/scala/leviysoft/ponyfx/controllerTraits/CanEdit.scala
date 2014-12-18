package leviysoft.ponyfx.controllerTraits

import leviysoft.ponyfx.OperationResult
import leviysoft.ponyfx.views.StronglyTypedView

trait CanEdit[T] {
  def edit(view: StronglyTypedView[T]): OperationResult[T]
}
