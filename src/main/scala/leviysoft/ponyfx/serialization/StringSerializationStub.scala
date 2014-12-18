package leviysoft.ponyfx.serialization

class StringSerializationStub extends Serializer[String] {
  override def serialize(instance: String): String = instance

  override def deserialize(representation: String): String = representation
}
