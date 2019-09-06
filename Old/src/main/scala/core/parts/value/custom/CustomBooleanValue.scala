package core.parts.value.custom

import core.entities.Entity
import core.entities.traits.properties.{PhysicsHolder, ValueHolder}
import core.parts.value.basic.BooleanValue
import core.repository.EntityRepository
import json.JValue

object CustomBooleanValue {
    
    final case class GetBooleanValue(entityId: Long, name: String) extends BooleanValue {
        override def get(implicit entityRepository: EntityRepository): Option[Boolean] = {
            entityRepository.getById(entityId) match {
                case en: ValueHolder => en.getValue(name) match {
                    case value: BooleanValue => value.get
                    case _ => None
                }
                case _ => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId,
                "name" -> name
            )
        }
    }
    
    final case class Exists(entityId: Long) extends BooleanValue {
        override def get(implicit entityRepository: EntityRepository): Option[Boolean] = {
            Some(entityRepository.contains(entityId))
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "entityId" -> entityId
            )
        }
    }
    
    final case class IsSolidAtCoordinates(value: CoordinatesValue) extends BooleanValue {
        override def get(implicit entityRepository: EntityRepository): Option[Boolean] = {
            val condition: Entity => Boolean = {
                case en: PhysicsHolder if en.physics.solid => true
                case _ => false
            }
            value.get match {
                case Some(v) => Some(entityRepository.existsAtCoordinates(v, condition))
                case None => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
    final case class IsOpaqueAtCoordinates(value: CoordinatesValue) extends BooleanValue {
        override def get(implicit entityRepository: EntityRepository): Option[Boolean] = {
            val condition: Entity => Boolean = {
                case en: PhysicsHolder if en.physics.opaque => true
                case _ => false
            }
            value.get match {
                case Some(v) => Some(entityRepository.existsAtCoordinates(v, condition))
                case None => None
            }
        }
        
        override def toJSON: JValue = {
            import json.MyJ._
            jObject(
                "class" -> this.getClass.getSimpleName,
                "value" -> value
            )
        }
    }
    
}