package demo

import demo.domain.Item
import demo.serialization.IntSerializer
import demo.views.ItemEditor
import leviysoft.ponyfx.JavaFXPonyApplication
import leviysoft.ponyfx.di.Bindings
import leviysoft.ponyfx.serialization.StringSerializationStub

trait DiConfiguration extends Bindings {
  bind[JavaFXPonyApplication]()
  bind[AppContainer]()
  bind[MainForm]()
  bind[ItemEditor]()
  bind[Item]()
  bind[ItemController]()
  bind[StringSerializationStub]()
  //bind[IntSerializer]()
}
