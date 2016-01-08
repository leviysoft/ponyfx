package leviysoft.ponyfx.util

import javafx.event.{ActionEvent, EventHandler}

import scala.language.implicitConversions

object EventListenerConstructor {
  implicit def makeListener(action: (ActionEvent) => Unit): EventHandler[ActionEvent] =
    new EventHandler[ActionEvent] {
      override def handle(event: ActionEvent): Unit = action(event)
    }
}
