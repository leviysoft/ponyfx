package ponyfx.di

import scala.reflect.runtime.universe._
import scala.collection.mutable.ListBuffer

/*
* This implementation is based on Jamie Pullar's simple DI container
* http://higher-state.blogspot.co.uk/2013/03/scala-210-simple-di-container.html
 */
trait Bindings {
  private val bindingsBuffer = new ListBuffer[(TypeSymbol, (ClassSymbol, MethodSymbol))]
  private val instanceBuffer = new ListBuffer[(TypeSymbol, Any)]
  private val excluded:Set[Symbol] = Set(typeOf[Any].typeSymbol, typeOf[Object].typeSymbol)

  protected lazy val registry:Map[TypeSymbol, (ClassSymbol, MethodSymbol)] = bindingsBuffer.toMap
  protected lazy val instanceRegistry:Map[TypeSymbol, Any] = instanceBuffer.toMap

  protected def bind[T:TypeTag]() {
    val types = getBaseTypes[T]
    val ctor = getConstructor[T]
    bindingsBuffer ++= types.map(_ ->(typeOf[T].typeSymbol.asClass, ctor))
  }

  protected def bind[T:TypeTag](instance:T) {
    val types = getBaseTypes[T]
    instanceBuffer ++= types.map(_ -> instance)
  }
  protected def bindAt[T:TypeTag](instance:T) {
    instanceBuffer += (typeOf[T].typeSymbol.asType -> instance)
  }

  private def getBaseTypes[T:TypeTag] = {
    val base = typeOf[T].baseClasses.map(_.asType)
    base.filter(!excluded.contains(_))
  }

  private def getConstructor[T:TypeTag] = typeOf[T].members.filter(_.isMethod).map(_.asMethod).filter(_.isConstructor).head
}
