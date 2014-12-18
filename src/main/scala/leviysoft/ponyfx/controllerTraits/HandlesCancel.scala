package leviysoft.ponyfx.controllerTraits

import leviysoft.ponyfx.OperationResult
import leviysoft.ponyfx.views.StronglyTypedView

trait HandlesCancel[T] {
  def onCancel(view: StronglyTypedView[T]): OperationResult[T]
}
