package leviysoft.ponyfx.controllerTraits

import leviysoft.ponyfx.OperationResult
import leviysoft.ponyfx.views.StronglyTypedView

trait HandlesIgnore[T] {
  def onIgnore(view: StronglyTypedView[T]): OperationResult[T]
}
