package leviysoft.ponyfx.controllerTraits

import leviysoft.ponyfx.OperationResult
import leviysoft.ponyfx.views.StronglyTypedView

trait HandlesAbort[T] {
  def onAbort(view: StronglyTypedView[T]): OperationResult[T]
}
