package core.parts.value

import core.entities.traits.properties.ValueHolder
import core.repository.EntityRepository
import value.FloatValue

object CustomFloatValue {
    
    final case class GetFloatValue(entityId: Long, name: String)
                                  (implicit entityRepository: EntityRepository) extends FloatValue {
        override def get: Option[Float] = entityRepository.getById(entityId) match {
            case en: ValueHolder => en.getValue(name) match {
                case value: FloatValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}
