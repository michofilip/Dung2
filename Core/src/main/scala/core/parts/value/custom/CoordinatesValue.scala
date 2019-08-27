package core.parts.value.custom

import core.entities.traits.properties.{PositionHolder, ValueHolder}
import core.parts.position.Coordinates
import core.repository.EntityRepository
import value.Value

abstract class CoordinatesValue extends Value {
    override final protected type T = Coordinates
}

object CoordinatesValue {
    
    final case object CoordinatesNull extends CoordinatesValue {
        override def get: Option[Coordinates] = None
    }
    
    final case class CoordinatesConstant(value: Coordinates) extends CoordinatesValue {
        override def get: Option[Coordinates] = Some(value)
    }
    
    final case class GetCoordinates(entityId: Long)
                                   (implicit entityRepository: EntityRepository) extends CoordinatesValue {
        override def get: Option[Coordinates] = entityRepository.getById(entityId) match {
            case Some(en: PositionHolder) => Some(en.position.coordinates)
            case _ => None
        }
    }
    
    final case class GetCoordinatesValue(entityId: Long, name: String)
                                        (implicit entityRepository: EntityRepository) extends CoordinatesValue {
        override def get: Option[Coordinates] = entityRepository.getById(entityId) match {
            case en: ValueHolder => en.getValue(name) match {
                case value: CoordinatesValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}