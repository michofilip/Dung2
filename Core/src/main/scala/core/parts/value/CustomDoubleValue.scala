package core.parts.value

import core.entities.traits.properties.ValueProperty
import core.repository.EntityRepository
import value.DoubleValue

object CustomDoubleValue {
    
    final case class GetDoubleValue(entityId: Long, name: String)
                                   (implicit entityRepository: EntityRepository) extends DoubleValue {
        override def get: Option[Double] = entityRepository.getById(entityId) match {
            case en: ValueProperty => en.getValue(name) match {
                case value: DoubleValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}
