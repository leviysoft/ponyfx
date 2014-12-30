package demo

import demo.domain.Item
import demo.serialization.IntSerializer
import demo.views.ItemEditor
import leviysoft.ponyfx.controllerTraits.{HandlesErrors, HandlesCancel, CanEdit, CanCreate}
import leviysoft.ponyfx.views.StronglyTypedView
import leviysoft.ponyfx.{PonyApplication, JavaFXPonyApplication}
import leviysoft.ponyfx.serialization.{Serializer, StringSerializationStub}
import scaldi.{Injector, Module}

class DiConfiguration extends Module {
  val inj: Injector = this

  bind[PonyApplication] to injected[JavaFXPonyApplication]
  bind[MainForm] to injected[MainForm]
  bind[StronglyTypedView[Item]] to injected[ItemEditor]
  bind[Item] to new Item()
  bind[CanCreate[Item]] to injected[ItemController]
  bind[CanEdit[Item]] to injected[ItemController]
  bind[HandlesCancel[Item]] to injected[ItemController]
  bind[HandlesErrors[Item]] to injected[ItemController]
  bind[Serializer[String]] to injected[StringSerializationStub]
  bind[Serializer[Int]] to injected[IntSerializer]
}
