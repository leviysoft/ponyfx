package demo

import demo.domain.Item
import leviysoft.ponyfx.{OperationStatus, OperationResult}
import leviysoft.ponyfx.controllerTraits.{CanCreate, CanEdit, HandlesErrors, HandlesCancel}
import leviysoft.ponyfx.views.StronglyTypedView

class ItemController extends CanCreate[Item] with CanEdit[Item] with HandlesCancel[Item] with HandlesErrors[Item] {
  override def create(view: StronglyTypedView[Item]): OperationResult[Item] =
    new OperationResult(OperationStatus.Completed, view.model)

  override def edit(view: StronglyTypedView[Item]): OperationResult[Item] =
    new OperationResult(OperationStatus.Completed, view.model)

  override def onCancel(view: StronglyTypedView[Item]): OperationResult[Item] =
    new OperationResult(OperationStatus.Cancelled, view.model)

  override def onError(view: StronglyTypedView[Item]): OperationResult[Item] =
    new OperationResult(OperationStatus.Error, view.model)
}
