package demo

import leviysoft.ponyfx.JavaFXPonyApplication
import leviysoft.ponyfx.di.Bindings

trait DiConfiguration extends Bindings {
  bind[JavaFXPonyApplication]()
  bind[AppContainer]()
  bind[MainForm]()
}
