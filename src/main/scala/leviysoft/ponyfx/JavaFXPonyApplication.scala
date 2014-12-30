package leviysoft.ponyfx

import java.beans.Introspector.decapitalize
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import leviysoft.ponyfx.controllerTraits._
import leviysoft.ponyfx.serialization.Serializer
import leviysoft.ponyfx.views.DialogResult.DialogResult
import leviysoft.ponyfx.views.{DialogResult, SimpleView, StronglyTypedView}
import scaldi.{Injectable, Injector}

import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}

class JavaFXPonyApplication(implicit private val inj: Injector) extends PonyApplication with Injectable{
  private def resolveStageResources[T : TypeTag](view: T, caption: Option[String] = None): T = {
    val loader = new FXMLLoader(view.getClass.getResource(s"${decapitalize(view.getClass.getSimpleName)}.fxml"))
    loader.setController(view)
    val root = loader.load[Parent]()
    view.asInstanceOf[Stage].setTitle(caption.getOrElse(view.getClass.getSimpleName))
    view.asInstanceOf[Stage].setScene(new Scene(root, root.prefWidth(-1.0), root.prefHeight(-1.0)))
    view
  }

  override def create[T: TypeTag](): OperationResult[T] = {
    val view = inject[StronglyTypedView[T]]
    view.model = inject[T]
    val viewStage = resolveStageResources(view)
    viewStage.showAndWait()

    processDialogResult(viewStage.viewResult, viewStage, (v) => inject[CanCreate[T]].create(v))
  }

  override def serializerOf[T: TypeTag](): Serializer[T] = {
    inject[Serializer[T]]
  }

  override def edit[T: TypeTag](model: T): OperationResult[T] = {
    val view = inject[StronglyTypedView[T]]
    view.model = model
    val viewStage = resolveStageResources(view.asInstanceOf[Stage]).asInstanceOf[StronglyTypedView[T]]
    viewStage.showAndWait()

    processDialogResult(viewStage.viewResult, viewStage, (v) => inject[CanEdit[T]].edit(v))
  }

  override def show[TView <: Stage with SimpleView : TypeTag](): DialogResult = {
    var view = inject[TView]
    view = resolveStageResources(view)
    view.showAndWait()
    view.viewResult
  }

  private def processDialogResult[T: TypeTag](
    dialogResult: DialogResult,
    view: StronglyTypedView[T],
    defaultAction: StronglyTypedView[T] => OperationResult[T]): OperationResult[T] = {
    dialogResult match  {
      case DialogResult.OK | DialogResult.Yes | DialogResult.No => defaultAction(view)
      case DialogResult.Abort =>
        val abortHandleResult = Try(inject[HandlesAbort[T]]).map(h => h.onAbort(view))
        lazy val errorHandleResult = Try(inject[HandlesErrors[T]]).map(h => h.onError(view))
        abortHandleResult match {
          case Success(abRes) => abRes
          case Failure(_) => errorHandleResult match {
            case Success(erRes) => erRes
            case Failure(_) => defaultAction(view)
          }
        }
      case DialogResult.Cancel =>
        val cancelHandleResult = Try(inject[HandlesCancel[T]]).map(h => h.onCancel(view))
        lazy val errorHandleResult = Try(inject[HandlesErrors[T]]).map(h => h.onError(view))
        cancelHandleResult match {
          case Success(canRes) => canRes
          case Failure(_) => errorHandleResult match {
            case Success(erRes) => erRes
            case Failure(_) => defaultAction(view)
          }
        }
      case DialogResult.Ignore =>
        val ignoreHandleResult = Try(inject[HandlesIgnore[T]]).map(h => h.onIgnore(view))
        lazy val errorHandleResult = Try(inject[HandlesErrors[T]]).map(h => h.onError(view))
        ignoreHandleResult match {
          case Success(igRes) => igRes
          case Failure(_) => errorHandleResult match {
            case Success(erRes) => erRes
            case Failure(_) => defaultAction(view)
          }
        }
      case DialogResult.Retry =>
        val retryHandleResult = Try(inject[HandlesRetry[T]]).map(h => h.onRetry(view))
        lazy val errorHandleResult = Try(inject[HandlesErrors[T]]).map(h => h.onError(view))
        retryHandleResult match {
          case Success(retRes) => retRes
          case Failure(_) => errorHandleResult match {
            case Success(erRes) => erRes
            case Failure(_) => defaultAction(view)
          }
        }
      case _ => defaultAction(view)
    }
  }
}
