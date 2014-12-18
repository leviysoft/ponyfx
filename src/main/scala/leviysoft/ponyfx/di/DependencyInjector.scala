package leviysoft.ponyfx.di

import scala.reflect.runtime.universe._
import scala.reflect.runtime.currentMirror

/*
* This implementation is based on Jamie Pullar's simple DI container
* http://higher-state.blogspot.co.uk/2013/03/scala-210-simple-di-container.html
 */
trait DependencyInjector extends Container {
  this:Bindings =>

  def get[T:TypeTag]:T = getInstance(typeOf[T].typeSymbol.asType).asInstanceOf[T]

  def get[T:TypeTag](args:Any*):T = getInstance(typeOf[T].typeSymbol.asType, args.toSet).asInstanceOf[T]

  protected def getInstance(typeSymbol:TypeSymbol, args:Set[Any] = Set.empty):Any =
    args.find(a => currentMirror.reflect(a).symbol.toType <:< typeSymbol.toType).getOrElse{
      instanceRegistry.getOrElse(typeSymbol, {
        val (clazz, ctor) = registry(typeSymbol)
        val ctorArgs = ctor.asMethod.paramLists.head map (p => getInstance(p.typeSignature.typeSymbol.asType, args))
        currentMirror.reflectClass(clazz).reflectConstructor(ctor).apply(ctorArgs:_*)
      })
    }
}