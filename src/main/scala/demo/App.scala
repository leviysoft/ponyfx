package demo

import javafx.application.Application

object App {
  def main(args: String*) {
    val appClass: Class[_ <: Application] = classOf[DemoApplication]
    Application.launch(appClass, args:_*)
  }
}
