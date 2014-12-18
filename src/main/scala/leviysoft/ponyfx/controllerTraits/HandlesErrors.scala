package leviysoft.ponyfx.controllerTraits

import leviysoft.ponyfx.OperationResult
import leviysoft.ponyfx.views.StronglyTypedView

trait HandlesErrors[T] {
  def onError(view: StronglyTypedView[T]): OperationResult[T]
}
