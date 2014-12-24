package demo

import demo.domain.Item
import demo.views.ItemEditor
import leviysoft.ponyfx.JavaFXPonyApplication
import leviysoft.ponyfx.di.Bindings

trait DiConfiguration extends Bindings {
  bind[JavaFXPonyApplication]()
  bind[AppContainer]()
  bind[MainForm]()
  bind[ItemEditor]()
  bind[Item]()
  bind[ItemController]()
}
