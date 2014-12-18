package leviysoft.ponyfx

import leviysoft.ponyfx.OperationStatus.OperationStatus

case class OperationResult[T](status: OperationStatus, result: T)
