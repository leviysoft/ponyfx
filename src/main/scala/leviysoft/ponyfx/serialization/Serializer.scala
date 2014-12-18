package leviysoft.ponyfx.serialization

trait Serializer[T] {
  def serialize(instance: T): String
  def deserialize(representation: String): T
}
