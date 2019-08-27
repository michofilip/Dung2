package core.parts.value

import core.entities.Entity
import core.entities.traits.properties.{PhysicsHolder, ValueHolder}
import core.repository.EntityRepository
import value.BooleanValue

object CustomBooleanValue {
    
    private val isSolid: Entity => Boolean = {
        case en: PhysicsHolder => en.physics.solid
        case _ => false
    }
    
    private val isOpaque: Entity => Boolean = {
        case en: PhysicsHolder => en.physics.opaque
        case _ => false
    }
    
    final case class GetBooleanValue(entityId: Long, name: String)
                                    (implicit entityRepository: EntityRepository) extends BooleanValue {
        override def get: Option[Boolean] = entityRepository.getById(entityId) match {
            case en: ValueHolder => en.getValue(name) match {
                case value: BooleanValue => value.get
                case _ => None
            }
            case _ => None
        }
    }
    
    final case class Exists(entityId: Long)(implicit entityRepository: EntityRepository) extends BooleanValue {
        override def get: Option[Boolean] = Some(entityRepository.contains(entityId))
    }
    
    final case class IsSolidAtCoordinates(value: CoordinatesValue)
                                         (implicit entityRepository: EntityRepository) extends BooleanValue {
        override def get: Option[Boolean] = value.get match {
            case Some(v) => Some(entityRepository.existsAtCoordinates(v, isSolid))
            case None => None
        }
    }
    
    final case class IsOpaqueAtCoordinates(value: CoordinatesValue)
                                          (implicit entityRepository: EntityRepository) extends BooleanValue {
        override def get: Option[Boolean] = value.get match {
            case Some(v) => Some(entityRepository.existsAtCoordinates(v, isOpaque))
            case None => None
        }
    }
    
}
