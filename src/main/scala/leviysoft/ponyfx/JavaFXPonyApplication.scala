package leviysoft.ponyfx

import java.beans.Introspector.decapitalize
import java.net.URL
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import leviysoft.ponyfx.controllerTraits._
import leviysoft.ponyfx.di.Container
import leviysoft.ponyfx.serialization.Serializer
import leviysoft.ponyfx.views.DialogResult.DialogResult
import leviysoft.ponyfx.views.{DialogResult, SimpleView, StronglyTypedView}

import scala.reflect.runtime.universe._
import scala.util.{Try, Success, Failure}

class JavaFXPonyApplication(private val container: Container) extends PonyApplication{
  private def resolveResourceUrl[T: TypeTag](): URL = {
    val clazz = typeTag[T].mirror.runtimeClass(typeOf[T])
    clazz.getResource(s"${decapitalize(clazz.getSimpleName)}.fxml")
  }

  private def resolveStageResources[T : TypeTag](view: T, caption: Option[String] = None): T = {
    val loader = new FXMLLoader(resolveResourceUrl[T]())
    loader.setController(view)
    val root = loader.load[Parent]()
    view.asInstanceOf[Stage].setTitle(caption.getOrElse(view.getClass.getSimpleName))
    view.asInstanceOf[Stage].setScene(new Scene(root, root.prefWidth(-1.0), root.prefHeight(-1.0)))
    view
  }

  override def create[T: TypeTag](): OperationResult[T] = {
    val view = container.get[StronglyTypedView[T]]
    view.model = container.get[T]
    val viewStage = resolveStageResources(view)
    viewStage.showAndWait()

    processDialogResult(viewStage.viewResult, viewStage, (v) => container.get[CanCreate[T]].create(v))
  }

  override def getSerializer[T: TypeTag]: Serializer[T] = {
    container.get[Serializer[T]]
  }

  override def edit[T: TypeTag](model: T): OperationResult[T] = {
    val view = container.get[StronglyTypedView[T]]
    view.model = model
    val viewStage = resolveStageResources(view.asInstanceOf[Stage]).asInstanceOf[StronglyTypedView[T]]
    viewStage.showAndWait()

    processDialogResult(viewStage.viewResult, viewStage, (v) => container.get[CanEdit[T]].edit(v))
  }

  override def show[TView <: Stage with SimpleView : TypeTag](): DialogResult = {
    var view = container.get[TView]
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
        val abortHandleResult = Try(container.get[HandlesAbort[T]]).map(h => h.onAbort(view))
        lazy val errorHandleResult = Try(container.get[HandlesErrors[T]]).map(h => h.onError(view))
        abortHandleResult match {
          case Success(abRes) => abRes
          case Failure(_) => errorHandleResult match {
            case Success(erRes) => erRes
            case Failure(_) => defaultAction(view)
          }
        }
      case DialogResult.Cancel =>
        val cancelHandleResult = Try(container.get[HandlesCancel[T]]).map(h => h.onCancel(view))
        lazy val errorHandleResult = Try(container.get[HandlesErrors[T]]).map(h => h.onError(view))
        cancelHandleResult match {
          case Success(canRes) => canRes
          case Failure(_) => errorHandleResult match {
            case Success(erRes) => erRes
            case Failure(_) => defaultAction(view)
          }
        }
      case DialogResult.Ignore =>
        val ignoreHandleResult = Try(container.get[HandlesIgnore[T]]).map(h => h.onIgnore(view))
        lazy val errorHandleResult = Try(container.get[HandlesErrors[T]]).map(h => h.onError(view))
        ignoreHandleResult match {
          case Success(igRes) => igRes
          case Failure(_) => errorHandleResult match {
            case Success(erRes) => erRes
            case Failure(_) => defaultAction(view)
          }
        }
      case DialogResult.Retry =>
        val retryHandleResult = Try(container.get[HandlesRetry[T]]).map(h => h.onRetry(view))
        lazy val errorHandleResult = Try(container.get[HandlesErrors[T]]).map(h => h.onError(view))
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
