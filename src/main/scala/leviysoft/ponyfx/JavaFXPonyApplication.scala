package leviysoft.ponyfx

import java.beans.Introspector.decapitalize
import java.net.URL
import javafx.fxml.FXMLLoader
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

import leviysoft.ponyfx.di.Container
import leviysoft.ponyfx.serialization.Serializer
import leviysoft.ponyfx.views.DialogResult.DialogResult
import leviysoft.ponyfx.views.SimpleView

import scala.reflect.runtime.universe._

class JavaFXPonyApplication(private val container: Container) extends PonyApplication{
  private def resolveResourceUrl[T: TypeTag](): URL = {
    val clazz = typeTag[T].mirror.runtimeClass(typeOf[T])
    clazz.getResource(s"${decapitalize(clazz.getSimpleName)}.fxml")
  }

  private def createStage[T : TypeTag](caption: Option[String]): T = {
    val view = container.get[T]
    val viewStage = view.asInstanceOf[Stage]
    val loader = new FXMLLoader(resolveResourceUrl[T]())
    loader.setController(view)
    val root = loader.load[Parent]
    viewStage.setTitle(caption.getOrElse(view.getClass.getSimpleName))
    viewStage.setScene(new Scene(root, root.prefWidth(-1.0), root.prefHeight(-1.0)))
    view
  }

  override def create[T](): OperationResult[T] = ???

  override def getSerializer[T]: Serializer[T] = ???

  override def edit[T](model: T): OperationResult[T] = ???

  override def show[TView <: SimpleView : TypeTag](): DialogResult = {
    val view = createStage[TView](None)
    view.showAndWait()
    view.viewResult
  }
}
