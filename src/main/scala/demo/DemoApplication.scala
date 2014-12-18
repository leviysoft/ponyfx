package demo

import javafx.application.Application
import javafx.stage.Stage

import leviysoft.ponyfx.TPonyApplication

class DemoApplication extends Application {
  override def start(primaryStage: Stage): Unit = {
    val container = new AppContainer
    val application = container.get[TPonyApplication]
  }
}
