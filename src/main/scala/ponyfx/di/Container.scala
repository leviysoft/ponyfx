package ponyfx.di

import scala.reflect.runtime.universe._

/*
* This implementation is based on Jamie Pullar's simple DI container
* http://higher-state.blogspot.co.uk/2013/03/scala-210-simple-di-container.html
 */
trait Container {
  def get[T: TypeTag]: T
  def get[T: TypeTag](args:Any*): T
}
