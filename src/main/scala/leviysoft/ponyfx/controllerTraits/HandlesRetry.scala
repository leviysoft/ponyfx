package leviysoft.ponyfx.controllerTraits

import leviysoft.ponyfx.OperationResult
import leviysoft.ponyfx.views.StronglyTypedView

trait HandlesRetry[T] {
  def onRetry(view: StronglyTypedView[T]): OperationResult[T]
}
