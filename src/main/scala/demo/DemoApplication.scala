package demo

import javafx.application.Application
import javafx.stage.Stage

import leviysoft.ponyfx.JavaFXPonyApplication

class DemoApplication extends Application {
  override def start(primaryStage: Stage): Unit = {
    implicit val injector = new DiConfiguration
    val application = new JavaFXPonyApplication()
    application.show[MainForm]()
  }
}
