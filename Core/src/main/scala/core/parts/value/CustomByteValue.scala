package core.parts.value

import core.entities.traits.properties.ValueProperty
import core.repository.EntityRepository
import value.ByteValue

object CustomByteValue {
    
    final case class GetByteValue(entityId: Long, name: String)
                                 (implicit entityRepository: EntityRepository) extends ByteValue {
        override def get: Option[Byte] = entityRepository.getById(entityId) match {
            case en: ValueProperty => en.getValue(name) match {
                case value: ByteValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}
