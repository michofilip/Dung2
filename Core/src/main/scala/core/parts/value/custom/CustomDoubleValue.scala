package core.parts.value.custom

import core.entities.traits.properties.ValueHolder
import core.repository.EntityRepository
import value.DoubleValue

object CustomDoubleValue {
    
    final case class GetDoubleValue(entityId: Long, name: String)
                                   (implicit entityRepository: EntityRepository) extends DoubleValue {
        override def get: Option[Double] = entityRepository.getById(entityId) match {
            case en: ValueHolder => en.getValue(name) match {
                case value: DoubleValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}
