package core.parts.value.custom

import core.entities.Entity
import core.entities.traits.properties.{PhysicsHolder, ValueHolder}
import core.repository.EntityRepository
import value.BooleanValue

object CustomBooleanValue {
    
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
            case Some(v) =>
                val condition: Entity => Boolean = {
                    case en: PhysicsHolder => en.physics.solid
                    case _ => false
                }
                Some(entityRepository.existsAtCoordinates(v, condition))
            case None => None
        }
    }
    
    final case class IsOpaqueAtCoordinates(value: CoordinatesValue)
                                          (implicit entityRepository: EntityRepository) extends BooleanValue {
        override def get: Option[Boolean] = value.get match {
            case Some(v) =>
                val condition: Entity => Boolean = {
                    case en: PhysicsHolder => en.physics.opaque
                    case _ => false
                }
                Some(entityRepository.existsAtCoordinates(v, condition))
            case None => None
        }
    }
    
}