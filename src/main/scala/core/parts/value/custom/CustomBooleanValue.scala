package core.parts.value.custom

import core.entity.properties.PhysicsHolder
import core.entity.Entity
import core.entity.repositoy.EntityRepository
import core.parts.value.basic.BooleanValue
import json.JValue

object CustomBooleanValue {
    
    final case class Exists(entityId: String) extends BooleanValue {
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            Some(entityHolder.contains(entityId))
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
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            val condition: Entity => Boolean = {
                case en: PhysicsHolder if en.physics.solid => true
                case _ => false
            }
            value.get match {
                case Some(v) => Some(entityHolder.existsAtCoordinates(v, condition))
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
        override def get(implicit entityHolder: EntityRepository): Option[Boolean] = {
            val condition: Entity => Boolean = {
                case en: PhysicsHolder if en.physics.opaque => true
                case _ => false
            }
            value.get match {
                case Some(v) => Some(entityHolder.existsAtCoordinates(v, condition))
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