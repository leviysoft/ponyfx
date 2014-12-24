package demo.serialization

import leviysoft.ponyfx.serialization.Serializer

class IntSerializer extends Serializer[Int] {
  override def serialize(instance: Int): String = instance.toString

  override def deserialize(representation: String): Int = representation.toInt
}
