package core.parts.value

import core.entities.traits.properties.{PositionProperty, ValueProperty}
import core.parts.position.Coordinates
import core.repository.EntityRepository
import value.Value

abstract class CoordinatesValue extends Value {
    override final protected type T = Coordinates
    
    override final def calculate: CoordinatesValue = CoordinatesValue.CoordinatesCalculate(this)
}

object CoordinatesValue {
    
    final case object CoordinatesNull extends CoordinatesValue {
        override def get: Option[Coordinates] = None
    }
    
    final case class CoordinatesConstant(value: Coordinates) extends CoordinatesValue {
        override def get: Option[Coordinates] = Some(value)
    }
    
    final case class CoordinatesCalculate(value: CoordinatesValue) extends CoordinatesValue {
        private val calculated: Option[Coordinates] = value.get
        
        override def get: Option[Coordinates] = calculated
    }
    
    final case class GetCoordinates(entityId: Long)(implicit entityRepository: EntityRepository) extends CoordinatesValue {
        override def get: Option[Coordinates] = entityRepository.getById(entityId) match {
            case Some(en: PositionProperty) => Some(en.position.coordinates)
            case _ => None
        }
    }
    
    final case class GetCoordinatesValue(entityId: Long, name: String)(implicit entityRepository: EntityRepository) extends CoordinatesValue {
        override def get: Option[Coordinates] = entityRepository.getById(entityId) match {
            case en: ValueProperty => en.getValue(name) match {
                case value: CoordinatesValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
}