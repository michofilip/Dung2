package core.parts.value

import core.entities.traits.properties.{PositionProperty, ValueProperty}
import core.parts.position.Direction
import core.repository.EntityRepository
import value.Value

abstract class DirectionValue extends Value {
    override final protected type T = Direction
    
    override final def calculate: DirectionValue = DirectionValue.DirectionCalculate(this)
}

object DirectionValue {
    
    final case object DirectionNull extends DirectionValue {
        override def get: Option[Direction] = None
    }
    
    final case class DirectionConstant(value: Direction) extends DirectionValue {
        override def get: Option[Direction] = Some(value)
    }
    
    final case class DirectionCalculate(value: DirectionValue) extends DirectionValue {
        private val calculated: Option[Direction] = value.get
        
        override def get: Option[Direction] = calculated
    }
    
    final case class GetDirection(entityId: Long)
                                 (implicit entityRepository: EntityRepository) extends DirectionValue {
        override def get: Option[Direction] = entityRepository.getById(entityId) match {
            case Some(en: PositionProperty) => Some(en.position.direction)
            case _ => None
        }
    }
    
    final case class GetDirectionValue(entityId: Long, name: String)
                                      (implicit entityRepository: EntityRepository) extends DirectionValue {
        override def get: Option[Direction] = entityRepository.getById(entityId) match {
            case en: ValueProperty => en.getValue(name) match {
                case value: DirectionValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}