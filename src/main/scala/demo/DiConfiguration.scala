package demo

import leviysoft.ponyfx.PonyApplication
import leviysoft.ponyfx.di.Bindings

trait DiConfiguration extends Bindings {
  bind[PonyApplication]()
}
