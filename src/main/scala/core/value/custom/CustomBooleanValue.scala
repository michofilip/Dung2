package core.value.custom

import core.entity.Entity.PhysicsHolder
import core.entity.{Entity, EntityHolder}
import core.value.basic.BooleanValue
import json.JValue

object CustomBooleanValue {
    
    final case class IsSolidAtCoordinates(value: CoordinatesValue) extends BooleanValue {
        override def get(implicit entityHolder: EntityHolder): Option[Boolean] = {
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
        override def get(implicit entityHolder: EntityHolder): Option[Boolean] = {
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