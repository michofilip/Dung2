package core.parts.value

import core.entities.traits.properties.{PositionHolder, ValueHolder}
import core.parts.position.Direction
import core.repository.EntityRepository
import value.Value

abstract class DirectionValue extends Value {
    override final protected type T = Direction
}

object DirectionValue {
    
    final case object DirectionNull extends DirectionValue {
        override def get: Option[Direction] = None
    }
    
    final case class DirectionConstant(value: Direction) extends DirectionValue {
        override def get: Option[Direction] = Some(value)
    }
    
    final case class GetDirection(entityId: Long)
                                 (implicit entityRepository: EntityRepository) extends DirectionValue {
        override def get: Option[Direction] = entityRepository.getById(entityId) match {
            case Some(en: PositionHolder) => Some(en.position.direction)
            case _ => None
        }
    }
    
    final case class GetDirectionValue(entityId: Long, name: String)
                                      (implicit entityRepository: EntityRepository) extends DirectionValue {
        override def get: Option[Direction] = entityRepository.getById(entityId) match {
            case en: ValueHolder => en.getValue(name) match {
                case value: DirectionValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}